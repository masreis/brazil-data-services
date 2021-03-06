package net.brazildata.weather.domain;

public enum Frequency {
  YEARLY("%Y"),
  MONTHLY("%Y-%m"),
  WEEKLY("%Y-%m"),
  DAILY("%Y-%m-%d");

  private String pattern;

  Frequency(String value) {
    this.pattern = value;
  }

  public String getPattern() {
    return pattern;
  }
}
