package enums;

import lombok.AllArgsConstructor;

/**
 * Created by Huy on 21/01/2017.
 */
@SuppressWarnings("unused")
@AllArgsConstructor
public enum MarkdownDecorator {

    CODE_BLOCK("`"),
    CODE_BLOCK_MULTILINE("```"),
    BOLD("**"),
    ITALIC("*"),
    BOLD_ITALIC("***"),
    STRIKEOUT("~~"),
    UNDERLINE("__"),
    UNDERLINE_ITALIC("__*"),
    UNDERLINE_BOLD("__**"),
    UNDERLINE_BOLD_ITALIC("__***");

    private final String decorator;

    @Override
    public String toString() {
        return this.decorator;
    }

}
