package resources;

public class NumberConverter {

  public static String reduce(int number) {
    if( number < 10000 ) {
      return number + "";
    }
    else if( number < 10000000 ) {
      return number/1000 + "K";
    }
    else {
      return number/1000000 + "M";
    }
  }
}
