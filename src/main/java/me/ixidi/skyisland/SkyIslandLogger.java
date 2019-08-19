package me.ixidi.skyisland;

import me.ixidi.skyisland.util.error.SkyIslandException;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class SkyIslandLogger extends Logger {

    private static final String DEFAULT_SOLUTION = "Contact plugin developer or check official error tracker.";

    private String prefix;

    public SkyIslandLogger(String name, String prefix) {
        super(name, null);
        this.prefix = String.format("[%s]", prefix);
    }

    @Override
    public void log(LogRecord record) {
        record.setMessage(String.format("%s %s", this.prefix, record.getMessage()));
        super.log(record);
    }

    public void throwable(Throwable throwable) {
        String solution;
        if (throwable instanceof SkyIslandException) {
            solution = ((SkyIslandException) throwable).getSolution();
        } else {
            solution = DEFAULT_SOLUTION;
        }
        severe("");
        severe("An error occurred.");
        severe("");
        severe("Type: " + throwable.getClass().getSimpleName());
        severe("Cause: " + throwable.getMessage());
        severe("Stacktrace: " + ExceptionUtils.getFullStackTrace(throwable));
        severe("");
        severe("Recommended solution: " + solution);
        severe("Official error tracker: " + SkyIslandPlugin.ISSUE_URL);
        severe("");
    }
}
