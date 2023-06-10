package techinterviewbot.utilities

public object StringUtil {

    public var language: Language = Language.RUSSIAN

    public fun getString(stringRes: StringLocalization): String {
        return when (language) {
            Language.RUSSIAN -> stringRes.russian
        }
    }
}
