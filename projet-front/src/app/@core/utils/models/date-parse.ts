export class DateParse {
  public static changeFormat (date: string | undefined): string | undefined{
    return date?.split("T")[0];
  }
}
