package techinterviewbot.utilities

public sealed interface StringLocalization {

    public val russian: String

    public fun get(): String {
        return StringUtil.getString(this)
    }
}

