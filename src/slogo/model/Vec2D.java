package slogo.model;

/**
 * 2D Vector
 */
public final class Vec2D {

  private double x = 0;
  private double y = 0;

  public Vec2D() {
  }

  public Vec2D(double x, double y) {
    this.set(x, y);
  }

  /**
   * Copy constructor
   */
  public Vec2D(Vec2D other) {
    this.x = other.x;
    this.y = other.y;
  }

  /**
   * Get x coordinate
   */
  public double getX() {
    return x;
  }

  /**
   * Get y coordinate
   */
  public double getY() {
    return y;
  }

  /**
   * Set x coordinate
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Set y coordinate
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Set x and y coordinate
   */
  public void set(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Dot product
   */
  public double dot(Vec2D other) {
    return x * other.x + y * other.y;
  }

  /**
   * Calculate the square of magnitude
   */
  public double magnitude2() {
    return x * x + y * y;
  }

  /**
   * Calculate magnitude
   */
  public double magnitude() {
    return Math.sqrt(magnitude2());
  }

  /**
   * Normalize the vector so that the magnitude is 1
   */
  public void normalize() {
    x /= magnitude();
    y /= magnitude();
  }

  /**
   * Element-wise addition
   */
  public Vec2D add(Vec2D v) {
    Vec2D ret = new Vec2D(this);
    ret.x += v.x;
    ret.y += v.y;
    return ret;
  }

  /**
   * Element-wise subtraction
   */
  public Vec2D minus(Vec2D v) {
    Vec2D ret = new Vec2D(this);
    ret.x -= v.x;
    ret.y -= v.y;
    return ret;
  }

  /**
   * Multiply by a scalar
   */
  public Vec2D mul(double v) {
    Vec2D ret = new Vec2D(this);
    ret.x *= v;
    ret.y *= v;
    return ret;
  }

  /**
   * Calculate the cosine of the angle between two vectors
   */
  public double cosAngle(Vec2D other) {
    return dot(other) / (magnitude() * other.magnitude());
  }

  /**
   * Calculate the projection of this vector to the other one
   */
  public Vec2D proj(Vec2D other) {
    double cosAngle = cosAngle(other);
    return other.mul(cosAngle);
  }

  /**
   * Calculate the perpendicular vector from the other vector pointing towards this
   */
  public Vec2D perp(Vec2D other) {
    Vec2D proj = proj(other);
    return minus(proj);
  }

  /**
   * The clockwiseness of rotation from other to this
   * <p>
   * https://gamedev.stackexchange.com/questions/45412/understanding-math-used-to-determine-if-vector-is-clockwise-counterclockwise-f
   * <p>
   * NOTE: assuming x is towards RIGHT, y is towards UP
   *
   * @return 1 if clockwise, 0 if no rotation, -1 if counterclockwise
   */
  @SuppressWarnings("SuspiciousNameCombination")
  public int clockwiseness(Vec2D other) {
    Vec2D tmp = new Vec2D(this.y, -this.x); // rotate this 90 degrees clockwise
    return (int) Math.signum(tmp.dot(other));
  }


  /**
   * Convert to string representation for easier printing and debugging
   */
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
