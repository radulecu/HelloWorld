package ro.rasel.java.formatter;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Arrays;

public class MessageFormatterTest {
    public static void main(String[] args) throws ParseException {
        pluralTime(3, 1, 5);
        choiceMessage();
    }

    private static void pluralTime(long days, long hours, long minutes) throws ParseException {
        String daysString = "{0}";
        String hoursString = "{1}";
        String minutesString = "{2}";
        MessageFormat messageFormat =
                new MessageFormat(daysString + ":" + hoursString + ":" + minutesString);
        final ChoiceFormat newFormat =
                new ChoiceFormat(new double[]{0, 1, 2}, new String[]{"", "{0} day:", "{0} days"});
        final ChoiceFormat newFormat1 =
                new ChoiceFormat(new double[]{0, 1, 2}, new String[]{"", "{1} hour", "{1} hours"});
        final ChoiceFormat newFormat2 =
                new ChoiceFormat(new double[]{0, 1, 2}, new String[]{"", "{2} minute", "{2} minutes"});
        newFormat.setParseIntegerOnly(true);
        newFormat1.setParseIntegerOnly(true);
        newFormat2.setParseIntegerOnly(true);
        messageFormat.setFormatByArgumentIndex(0,
                newFormat);
        messageFormat.setFormatByArgumentIndex(1,
                newFormat1);
        messageFormat.setFormatByArgumentIndex(2,
                newFormat2);

        final String format = messageFormat.format(new Object[]{days,hours, minutes,days,hours, minutes});
        System.out.println(format);
        System.out.println(Arrays.asList(messageFormat.parse(format)));
    }

    private static void choiceMessage() throws ParseException {
        MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
        ChoiceFormat fileform = new ChoiceFormat("0#no files|1#one file|2<{0} files");
        form.setFormatByArgumentIndex(0, fileform);

        int fileCount = 1273;
        String diskName = "MyDisk";
        Object[] testArgs = {(long) fileCount, diskName};

        final String format = form.format(testArgs);
        System.out.println(format);
//        System.out.println(Arrays.asList(form.parse(format)));
    }


}
