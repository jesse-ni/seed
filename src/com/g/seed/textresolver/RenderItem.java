package com.g.seed.textresolver;

import android.text.style.CharacterStyle;

public class RenderItem
{
  private int start;
  private int length;
  private CharacterStyle[] styles;

  public RenderItem(int start, int length, CharacterStyle[] styles)
  {
    this.styles = styles;
    this.start = start;
    this.length = length;
  }

  public CharacterStyle[] getStyles()
  {
    return this.styles;
  }

  public void setStyles(CharacterStyle[] styles) {
    this.styles = styles;
  }

  public int getStart() {
    return this.start;
  }

  public int getEnd() {
    return (getStart() + getLength());
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getLength() {
    return this.length;
  }

  public void setLength(int length) {
    this.length = length;
  }
}