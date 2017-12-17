package util;

public class Util {

  public static String getFileUrl(String fqPackageName, String filename) {
    String path = Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    return "file://" + path + fqPackageName.replace(".", "/") + "/" + filename;
  }

}