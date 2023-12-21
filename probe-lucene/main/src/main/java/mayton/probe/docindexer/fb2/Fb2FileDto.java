package mayton.probe.docindexer.fb2;

public record Fb2FileDto(
    String genre,
    String authorFirstName,
    String authorLastName,
    String bookTitle,
    String date,
    String id,
    String text
) {};
