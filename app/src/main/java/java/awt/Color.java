package java.awt;


/**
 * This is just an example how a class from java.awt can
 * be included in the shaded code so that it can be used
 * on Android where some of these are missing.
 *
 * Taken from JDK 1.8.0_121_x64 on Windows
 */
public class Color {

    /**
     * The color white.  In the default sRGB space.
     */
    public final static java.awt.Color white     = new java.awt.Color(255, 255, 255);

    /**
     * The color white.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color WHITE = white;

    /**
     * The color light gray.  In the default sRGB space.
     */
    public final static java.awt.Color lightGray = new java.awt.Color(192, 192, 192);

    /**
     * The color light gray.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color LIGHT_GRAY = lightGray;

    /**
     * The color gray.  In the default sRGB space.
     */
    public final static java.awt.Color gray      = new java.awt.Color(128, 128, 128);

    /**
     * The color gray.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color GRAY = gray;

    /**
     * The color dark gray.  In the default sRGB space.
     */
    public final static java.awt.Color darkGray  = new java.awt.Color(64, 64, 64);

    /**
     * The color dark gray.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color DARK_GRAY = darkGray;

    /**
     * The color black.  In the default sRGB space.
     */
    public final static java.awt.Color black     = new java.awt.Color(0, 0, 0);

    /**
     * The color black.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color BLACK = black;

    /**
     * The color red.  In the default sRGB space.
     */
    public final static java.awt.Color red       = new java.awt.Color(255, 0, 0);

    /**
     * The color red.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color RED = red;

    /**
     * The color pink.  In the default sRGB space.
     */
    public final static java.awt.Color pink      = new java.awt.Color(255, 175, 175);

    /**
     * The color pink.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color PINK = pink;

    /**
     * The color orange.  In the default sRGB space.
     */
    public final static java.awt.Color orange    = new java.awt.Color(255, 200, 0);

    /**
     * The color orange.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color ORANGE = orange;

    /**
     * The color yellow.  In the default sRGB space.
     */
    public final static java.awt.Color yellow    = new java.awt.Color(255, 255, 0);

    /**
     * The color yellow.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color YELLOW = yellow;

    /**
     * The color green.  In the default sRGB space.
     */
    public final static java.awt.Color green     = new java.awt.Color(0, 255, 0);

    /**
     * The color green.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color GREEN = green;

    /**
     * The color magenta.  In the default sRGB space.
     */
    public final static java.awt.Color magenta   = new java.awt.Color(255, 0, 255);

    /**
     * The color magenta.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color MAGENTA = magenta;

    /**
     * The color cyan.  In the default sRGB space.
     */
    public final static java.awt.Color cyan      = new java.awt.Color(0, 255, 255);

    /**
     * The color cyan.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color CYAN = cyan;

    /**
     * The color blue.  In the default sRGB space.
     */
    public final static java.awt.Color blue      = new java.awt.Color(0, 0, 255);

    /**
     * The color blue.  In the default sRGB space.
     * @since 1.4
     */
    public final static java.awt.Color BLUE = blue;

    /**
     * The color value.
     * @serial
     * @see #getRGB
     */
    int value;

    /**
     * The color value in the default sRGB <code>ColorSpace</code> as
     * <code>float</code> components (no alpha).
     * If <code>null</code> after object construction, this must be an
     * sRGB color constructed with 8-bit precision, so compute from the
     * <code>int</code> color value.
     * @serial
     * @see #getRGBColorComponents
     * @see #getRGBComponents
     */
    private float frgbvalue[] = null;

    /**
     * The color value in the native <code>ColorSpace</code> as
     * <code>float</code> components (no alpha).
     * If <code>null</code> after object construction, this must be an
     * sRGB color constructed with 8-bit precision, so compute from the
     * <code>int</code> color value.
     * @serial
     * @see #getRGBColorComponents
     * @see #getRGBComponents
     */
    private float fvalue[] = null;

    /**
     * The alpha value as a <code>float</code> component.
     * If <code>frgbvalue</code> is <code>null</code>, this is not valid
     * data, so compute from the <code>int</code> color value.
     * @serial
     * @see #getRGBComponents
     * @see #getComponents
     */
    private float falpha = 0.0f;

    /**
     * Checks the color integer components supplied for validity.
     * Throws an {@link IllegalArgumentException} if the value is out of
     * range.
     * @param r the Red component
     * @param g the Green component
     * @param b the Blue component
     **/
    private static void testColorValueRange(int r, int g, int b, int a) {
        boolean rangeError = false;
        String badComponentString = "";

        if ( a < 0 || a > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Alpha";
        }
        if ( r < 0 || r > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Red";
        }
        if ( g < 0 || g > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Green";
        }
        if ( b < 0 || b > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Blue";
        }
        if ( rangeError == true ) {
            throw new IllegalArgumentException("Color parameter outside of expected range:"
                    + badComponentString);
        }
    }

    /**
     * Checks the color <code>float</code> components supplied for
     * validity.
     * Throws an <code>IllegalArgumentException</code> if the value is out
     * of range.
     * @param r the Red component
     * @param g the Green component
     * @param b the Blue component
     **/
    private static void testColorValueRange(float r, float g, float b, float a) {
        boolean rangeError = false;
        String badComponentString = "";
        if ( a < 0.0 || a > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Alpha";
        }
        if ( r < 0.0 || r > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Red";
        }
        if ( g < 0.0 || g > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Green";
        }
        if ( b < 0.0 || b > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Blue";
        }
        if ( rangeError == true ) {
            throw new IllegalArgumentException("Color parameter outside of expected range:"
                    + badComponentString);
        }
    }

    /**
     * Creates an opaque sRGB color with the specified red, green,
     * and blue values in the range (0 - 255).
     * The actual color used in rendering depends
     * on finding the best match given the color space
     * available for a given output device.
     * Alpha is defaulted to 255.
     *
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *        or <code>b</code> are outside of the range
     *        0 to 255, inclusive
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /**
     * Creates an sRGB color with the specified red, green, blue, and alpha
     * values in the range (0 - 255).
     *
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>,
     *        <code>b</code> or <code>a</code> are outside of the range
     *        0 to 255, inclusive
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public Color(int r, int g, int b, int a) {
        value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
        testColorValueRange(r,g,b,a);
    }

    /**
     * Creates an opaque sRGB color with the specified combined RGB value
     * consisting of the red component in bits 16-23, the green component
     * in bits 8-15, and the blue component in bits 0-7.  The actual color
     * used in rendering depends on finding the best match given the
     * color space available for a particular output device.  Alpha is
     * defaulted to 255.
     *
     * @param rgb the combined RGB components
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public Color(int rgb) {
        value = 0xff000000 | rgb;
    }

    /**
     * Creates an sRGB color with the specified combined RGBA value consisting
     * of the alpha component in bits 24-31, the red component in bits 16-23,
     * the green component in bits 8-15, and the blue component in bits 0-7.
     * If the <code>hasalpha</code> argument is <code>false</code>, alpha
     * is defaulted to 255.
     *
     * @param rgba the combined RGBA components
     * @param hasalpha <code>true</code> if the alpha bits are valid;
     *        <code>false</code> otherwise
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public Color(int rgba, boolean hasalpha) {
        if (hasalpha) {
            value = rgba;
        } else {
            value = 0xff000000 | rgba;
        }
    }

    /**
     * Creates an opaque sRGB color with the specified red, green, and blue
     * values in the range (0.0 - 1.0).  Alpha is defaulted to 1.0.  The
     * actual color used in rendering depends on finding the best
     * match given the color space available for a particular output
     * device.
     *
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *        or <code>b</code> are outside of the range
     *        0.0 to 1.0, inclusive
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public Color(float r, float g, float b) {
        this( (int) (r*255+0.5), (int) (g*255+0.5), (int) (b*255+0.5));
        testColorValueRange(r,g,b,1.0f);
        frgbvalue = new float[3];
        frgbvalue[0] = r;
        frgbvalue[1] = g;
        frgbvalue[2] = b;
        falpha = 1.0f;
        fvalue = frgbvalue;
    }

    /**
     * Creates an sRGB color with the specified red, green, blue, and
     * alpha values in the range (0.0 - 1.0).  The actual color
     * used in rendering depends on finding the best match given the
     * color space available for a particular output device.
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *        <code>b</code> or <code>a</code> are outside of the range
     *        0.0 to 1.0, inclusive
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public Color(float r, float g, float b, float a) {
        this((int)(r*255+0.5), (int)(g*255+0.5), (int)(b*255+0.5), (int)(a*255+0.5));
        frgbvalue = new float[3];
        frgbvalue[0] = r;
        frgbvalue[1] = g;
        frgbvalue[2] = b;
        falpha = a;
        fvalue = frgbvalue;
    }


    /**
     * Returns the red component in the range 0-255 in the default sRGB
     * space.
     * @return the red component.
     * @see #getRGB
     */
    public int getRed() {
        return (getRGB() >> 16) & 0xFF;
    }

    /**
     * Returns the green component in the range 0-255 in the default sRGB
     * space.
     * @return the green component.
     * @see #getRGB
     */
    public int getGreen() {
        return (getRGB() >> 8) & 0xFF;
    }

    /**
     * Returns the blue component in the range 0-255 in the default sRGB
     * space.
     * @return the blue component.
     * @see #getRGB
     */
    public int getBlue() {
        return (getRGB() >> 0) & 0xFF;
    }

    /**
     * Returns the alpha component in the range 0-255.
     * @return the alpha component.
     * @see #getRGB
     */
    public int getAlpha() {
        return (getRGB() >> 24) & 0xff;
    }

    /**
     * Returns the RGB value representing the color in the default sRGB

     * (Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are
     * blue).
     * @return the RGB value of the color in the default sRGB
     *         <code>ColorModel</code>.
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @since JDK1.0
     */
    public int getRGB() {
        return value;
    }

    private static final double FACTOR = 0.7;

    /**
     * Creates a new <code>Color</code> that is a brighter version of this
     * <code>Color</code>.
     * <p>
     * This method applies an arbitrary scale factor to each of the three RGB
     * components of this <code>Color</code> to create a brighter version
     * of this <code>Color</code>.
     * The {@code alpha} value is preserved.
     * Although <code>brighter</code> and
     * <code>darker</code> are inverse operations, the results of a
     * series of invocations of these two methods might be inconsistent
     * because of rounding errors.
     * @return     a new <code>Color</code> object that is
     *                 a brighter version of this <code>Color</code>
     *                 with the same {@code alpha} value.
     * @see        java.awt.Color#darker
     * @since      JDK1.0
     */
    public java.awt.Color brighter() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        int alpha = getAlpha();

        /* From 2D group:
         * 1. black.brighter() should return grey
         * 2. applying brighter to blue will always return blue, brighter
         * 3. non pure color (non zero rgb) will eventually return white
         */
        int i = (int)(1.0/(1.0-FACTOR));
        if ( r == 0 && g == 0 && b == 0) {
            return new java.awt.Color(i, i, i, alpha);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;

        return new java.awt.Color(Math.min((int)(r/FACTOR), 255),
                Math.min((int)(g/FACTOR), 255),
                Math.min((int)(b/FACTOR), 255),
                alpha);
    }

    /**
     * Creates a new <code>Color</code> that is a darker version of this
     * <code>Color</code>.
     * <p>
     * This method applies an arbitrary scale factor to each of the three RGB
     * components of this <code>Color</code> to create a darker version of
     * this <code>Color</code>.
     * The {@code alpha} value is preserved.
     * Although <code>brighter</code> and
     * <code>darker</code> are inverse operations, the results of a series
     * of invocations of these two methods might be inconsistent because
     * of rounding errors.
     * @return  a new <code>Color</code> object that is
     *                    a darker version of this <code>Color</code>
     *                    with the same {@code alpha} value.
     * @see        java.awt.Color#brighter
     * @since      JDK1.0
     */
    public java.awt.Color darker() {
        return new java.awt.Color(Math.max((int)(getRed()  *FACTOR), 0),
                Math.max((int)(getGreen()*FACTOR), 0),
                Math.max((int)(getBlue() *FACTOR), 0),
                getAlpha());
    }

    /**
     * Computes the hash code for this <code>Color</code>.
     * @return     a hash code value for this object.
     * @since      JDK1.0
     */
    public int hashCode() {
        return value;
    }

    /**
     * Determines whether another object is equal to this
     * <code>Color</code>.
     * <p>
     * The result is <code>true</code> if and only if the argument is not
     * <code>null</code> and is a <code>Color</code> object that has the same
     * red, green, blue, and alpha values as this object.
     * @param       obj   the object to test for equality with this
     *                          <code>Color</code>
     * @return      <code>true</code> if the objects are the same;
     *                             <code>false</code> otherwise.
     * @since   JDK1.0
     */
    public boolean equals(Object obj) {
        return obj instanceof java.awt.Color && ((java.awt.Color)obj).getRGB() == this.getRGB();
    }

    /**
     * Returns a string representation of this <code>Color</code>. This
     * method is intended to be used only for debugging purposes.  The
     * content and format of the returned string might vary between
     * implementations. The returned string might be empty but cannot
     * be <code>null</code>.
     *
     * @return  a string representation of this <code>Color</code>.
     */
    public String toString() {
        return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
    }

    /**
     * Converts a <code>String</code> to an integer and returns the
     * specified opaque <code>Color</code>. This method handles string
     * formats that are used to represent octal and hexadecimal numbers.
     * @param      nm a <code>String</code> that represents
     *                            an opaque color as a 24-bit integer
     * @return     the new <code>Color</code> object.
     * @see        Integer#decode
     * @exception  NumberFormatException  if the specified string cannot
     *                      be interpreted as a decimal,
     *                      octal, or hexadecimal integer.
     * @since      JDK1.1
     */
    public static java.awt.Color decode(String nm) throws NumberFormatException {
        Integer intval = Integer.decode(nm);
        int i = intval.intValue();
        return new java.awt.Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

    /**
     * Finds a color in the system properties.
     * <p>
     * The argument is treated as the name of a system property to
     * be obtained. The string value of this property is then interpreted
     * as an integer which is then converted to a <code>Color</code>
     * object.
     * <p>
     * If the specified property is not found or could not be parsed as
     * an integer then <code>null</code> is returned.
     * @param    nm the name of the color property
     * @return   the <code>Color</code> converted from the system
     *          property.
     * @see      System#getProperty(String)
     * @see      Integer#getInteger(String)
     * @see      java.awt.Color#Color(int)
     * @since    JDK1.0
     */
    public static java.awt.Color getColor(String nm) {
        return getColor(nm, null);
    }

    /**
     * Finds a color in the system properties.
     * <p>
     * The first argument is treated as the name of a system property to
     * be obtained. The string value of this property is then interpreted
     * as an integer which is then converted to a <code>Color</code>
     * object.
     * <p>
     * If the specified property is not found or cannot be parsed as
     * an integer then the <code>Color</code> specified by the second
     * argument is returned instead.
     * @param    nm the name of the color property
     * @param    v    the default <code>Color</code>
     * @return   the <code>Color</code> converted from the system
     *          property, or the specified <code>Color</code>.
     * @see      System#getProperty(String)
     * @see      Integer#getInteger(String)
     * @see      java.awt.Color#Color(int)
     * @since    JDK1.0
     */
    public static java.awt.Color getColor(String nm, java.awt.Color v) {
        Integer intval = Integer.getInteger(nm);
        if (intval == null) {
            return v;
        }
        int i = intval.intValue();
        return new java.awt.Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

    /**
     * Finds a color in the system properties.
     * <p>
     * The first argument is treated as the name of a system property to
     * be obtained. The string value of this property is then interpreted
     * as an integer which is then converted to a <code>Color</code>
     * object.
     * <p>
     * If the specified property is not found or could not be parsed as
     * an integer then the integer value <code>v</code> is used instead,
     * and is converted to a <code>Color</code> object.
     * @param    nm  the name of the color property
     * @param    v   the default color value, as an integer
     * @return   the <code>Color</code> converted from the system
     *          property or the <code>Color</code> converted from
     *          the specified integer.
     * @see      System#getProperty(String)
     * @see      Integer#getInteger(String)
     * @see      java.awt.Color#Color(int)
     * @since    JDK1.0
     */
    public static java.awt.Color getColor(String nm, int v) {
        Integer intval = Integer.getInteger(nm);
        int i = (intval != null) ? intval.intValue() : v;
        return new java.awt.Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, (i >> 0) & 0xFF);
    }

    /**
     * Converts the components of a color, as specified by the HSB
     * model, to an equivalent set of values for the default RGB model.
     * <p>
     * The <code>saturation</code> and <code>brightness</code> components
     * should be floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>hue</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * <p>
     * The integer that is returned by <code>HSBtoRGB</code> encodes the
     * value of a color in bits 0-23 of an integer value that is the same
     * format used by the method {@link #getRGB() getRGB}.
     * This integer can be supplied as an argument to the
     * <code>Color</code> constructor that takes a single integer argument.
     * @param     hue   the hue component of the color
     * @param     saturation   the saturation of the color
     * @param     brightness   the brightness of the color
     * @return    the RGB value of the color with the indicated hue,
     *                            saturation, and brightness.
     * @see       java.awt.Color#getRGB()
     * @see       java.awt.Color#Color(int)
     * @see       java.awt.image.ColorModel#getRGBdefault()
     * @since     JDK1.0
     */
    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float) Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
    }

    /**
     * Converts the components of a color, as specified by the default RGB
     * model, to an equivalent set of values for hue, saturation, and
     * brightness that are the three components of the HSB model.
     * <p>
     * If the <code>hsbvals</code> argument is <code>null</code>, then a
     * new array is allocated to return the result. Otherwise, the method
     * returns the array <code>hsbvals</code>, with the values put into
     * that array.
     * @param     r   the red component of the color
     * @param     g   the green component of the color
     * @param     b   the blue component of the color
     * @param     hsbvals  the array used to return the
     *                     three HSB values, or <code>null</code>
     * @return    an array of three elements containing the hue, saturation,
     *                     and brightness (in that order), of the color with
     *                     the indicated red, green, and blue components.
     * @see       java.awt.Color#getRGB()
     * @see       java.awt.Color#Color(int)
     * @see       java.awt.image.ColorModel#getRGBdefault()
     * @since     JDK1.0
     */
    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = (r > g) ? r : g;
        if (b > cmax) cmax = b;
        int cmin = (r < g) ? r : g;
        if (b < cmin) cmin = b;

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0)
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        else
            saturation = 0;
        if (saturation == 0)
            hue = 0;
        else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0f + redc - bluec;
            else
                hue = 4.0f + greenc - redc;
            hue = hue / 6.0f;
            if (hue < 0)
                hue = hue + 1.0f;
        }
        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }

    /**
     * Creates a <code>Color</code> object based on the specified values
     * for the HSB color model.
     * <p>
     * The <code>s</code> and <code>b</code> components should be
     * floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>h</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * @param  h   the hue component
     * @param  s   the saturation of the color
     * @param  b   the brightness of the color
     * @return  a <code>Color</code> object with the specified hue,
     *                                 saturation, and brightness.
     * @since   JDK1.0
     */
    public static java.awt.Color getHSBColor(float h, float s, float b) {
        return new java.awt.Color(HSBtoRGB(h, s, b));
    }

    /**
     * Returns a <code>float</code> array containing the color and alpha
     * components of the <code>Color</code>, as represented in the default
     * sRGB color space.
     * If <code>compArray</code> is <code>null</code>, an array of length
     * 4 is created for the return value.  Otherwise,
     * <code>compArray</code> must have length 4 or greater,
     * and it is filled in with the components and returned.
     * @param compArray an array that this method fills with
     *                  color and alpha components and returns
     * @return the RGBA components in a <code>float</code> array.
     */
    public float[] getRGBComponents(float[] compArray) {
        float[] f;
        if (compArray == null) {
            f = new float[4];
        } else {
            f = compArray;
        }
        if (frgbvalue == null) {
            f[0] = ((float)getRed())/255f;
            f[1] = ((float)getGreen())/255f;
            f[2] = ((float)getBlue())/255f;
            f[3] = ((float)getAlpha())/255f;
        } else {
            f[0] = frgbvalue[0];
            f[1] = frgbvalue[1];
            f[2] = frgbvalue[2];
            f[3] = falpha;
        }
        return f;
    }

    /**
     * Returns a <code>float</code> array containing only the color
     * components of the <code>Color</code>, in the default sRGB color
     * space.  If <code>compArray</code> is <code>null</code>, an array of
     * length 3 is created for the return value.  Otherwise,
     * <code>compArray</code> must have length 3 or greater, and it is
     * filled in with the components and returned.
     * @param compArray an array that this method fills with color
     *          components and returns
     * @return the RGB components in a <code>float</code> array.
     */
    public float[] getRGBColorComponents(float[] compArray) {
        float[] f;
        if (compArray == null) {
            f = new float[3];
        } else {
            f = compArray;
        }
        if (frgbvalue == null) {
            f[0] = ((float)getRed())/255f;
            f[1] = ((float)getGreen())/255f;
            f[2] = ((float)getBlue())/255f;
        } else {
            f[0] = frgbvalue[0];
            f[1] = frgbvalue[1];
            f[2] = frgbvalue[2];
        }
        return f;
    }

    /**
     * Returns a <code>float</code> array containing the color and alpha
     * components of the <code>Color</code>, in the
     * <code>ColorSpace</code> of the <code>Color</code>.
     * If <code>compArray</code> is <code>null</code>, an array with
     * length equal to the number of components in the associated
     * <code>ColorSpace</code> plus one is created for
     * the return value.  Otherwise, <code>compArray</code> must have at
     * least this length and it is filled in with the components and
     * returned.
     * @param compArray an array that this method fills with the color and
     *          alpha components of this <code>Color</code> in its
     *          <code>ColorSpace</code> and returns
     * @return the color and alpha components in a <code>float</code>
     *          array.
     */
    public float[] getComponents(float[] compArray) {
        if (fvalue == null)
            return getRGBComponents(compArray);
        float[] f;
        int n = fvalue.length;
        if (compArray == null) {
            f = new float[n + 1];
        } else {
            f = compArray;
        }
        for (int i = 0; i < n; i++) {
            f[i] = fvalue[i];
        }
        f[n] = falpha;
        return f;
    }

    /**
     * Returns a <code>float</code> array containing only the color
     * components of the <code>Color</code>, in the
     * <code>ColorSpace</code> of the <code>Color</code>.
     * If <code>compArray</code> is <code>null</code>, an array with
     * length equal to the number of components in the associated
     * <code>ColorSpace</code> is created for
     * the return value.  Otherwise, <code>compArray</code> must have at
     * least this length and it is filled in with the components and
     * returned.
     * @param compArray an array that this method fills with the color
     *          components of this <code>Color</code> in its
     *          <code>ColorSpace</code> and returns
     * @return the color components in a <code>float</code> array.
     */
    public float[] getColorComponents(float[] compArray) {
        if (fvalue == null)
            return getRGBColorComponents(compArray);
        float[] f;
        int n = fvalue.length;
        if (compArray == null) {
            f = new float[n];
        } else {
            f = compArray;
        }
        for (int i = 0; i < n; i++) {
            f[i] = fvalue[i];
        }
        return f;
    }
}
