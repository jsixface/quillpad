package org.qosp.notes.di

import android.content.Context
import android.text.style.BackgroundColorSpan
import android.text.util.Linkify
import android.util.TypedValue
import io.noties.markwon.*
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.handler.EmphasisEditHandler
import io.noties.markwon.editor.handler.StrongEmphasisEditHandler
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.linkify.LinkifyPlugin
import io.noties.markwon.movement.MovementMethodPlugin
import io.noties.markwon.simple.ext.SimpleExtPlugin
import me.saket.bettermovementmethod.BetterLinkMovementMethod
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.qosp.notes.R
import org.qosp.notes.data.sync.core.SyncManager
import org.qosp.notes.ui.editor.EditorFragment
import org.qosp.notes.ui.editor.markdown.*
import org.qosp.notes.ui.utils.coil.CoilImagesPlugin
import org.qosp.notes.ui.utils.resolveAttribute

object MarkwonModule {

    val module = module {
        scope<EditorFragment> {
            scoped { provideMarkwon(androidContext(), syncManager = get()) }
            scoped { provideMarkwonEditor(markwon = get()) }
        }
    }

    private fun provideMarkwon(context: Context, syncManager: SyncManager): Markwon {
        return Markwon.builder(context)
            .usePlugin(LinkifyPlugin.create(Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS))
            .usePlugin(SoftBreakAddsNewLinePlugin.create())
            .usePlugin(MovementMethodPlugin.create(BetterLinkMovementMethod.getInstance()))
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TablePlugin.create(context))
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureConfiguration(builder: MarkwonConfiguration.Builder) {
                    builder.linkResolver(LinkResolverDef())
                }
            })
            .usePlugin(SimpleExtPlugin.create { plugin: SimpleExtPlugin ->
                plugin
                    .addExtension(
                        2,
                        '=',
                        SpanFactory { _, _ ->
                            val typedValue = TypedValue()
                            context.theme.resolveAttribute(R.attr.colorNoteTextHighlight, typedValue, true)
                            val color = typedValue.data;
                            return@SpanFactory BackgroundColorSpan(color)
                        })
            })
            .usePlugin(CoilImagesPlugin.create(context, syncManager))
            .apply {
                val mainColor = context.resolveAttribute(R.attr.colorMarkdownTask) ?: return@apply
                val backgroundColor = context.resolveAttribute(R.attr.colorBackground) ?: return@apply
                usePlugin(TaskListPlugin.create(mainColor, mainColor, backgroundColor))
            }
            .build()

    }

    fun provideMarkwonEditor(markwon: Markwon): MarkwonEditor {
        return MarkwonEditor.builder(markwon)
            .useEditHandler(EmphasisEditHandler())
            .useEditHandler(StrongEmphasisEditHandler())
            .useEditHandler(CodeHandler())
            .useEditHandler(CodeBlockHandler())
            .useEditHandler(BlockQuoteHandler())
            .useEditHandler(StrikethroughHandler())
            .useEditHandler(HeadingHandler())
            .build()
    }
}
