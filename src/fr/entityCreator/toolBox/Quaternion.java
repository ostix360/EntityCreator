package fr.entityCreator.toolBox;


import org.joml.Matrix4f;

/**
 * A quaternion simply represents a 3D rotation. The maths behind it is quite
 * complex (literally; it involves complex numbers) so I wont go into it in too
 * much detail. The important things to note are that it represents a 3d
 * rotation, it's very easy to interpolate between two quaternion rotations
 * (which would not be easy to do correctly with Euler rotations or rotation
 * matrices), and you can convert to and from matrices fairly easily. So when we
 * need to interpolate between rotations we'll represent them as quaternions,
 * but when we need to apply the rotations to anything we'll convert back to a
 * matrix.
 * <p>
 * An quick introduction video to quaternions:
 * https://www.youtube.com/watch?v=SCbpxiCN0U0
 * <p>
 * and a slightly longer one:
 * https://www.youtube.com/watch?v=fKIss4EV6ME&t=0s
 *
 * @author Karl
 */
public class Quaternion {

    private float x, y, z, w;

    /**
     * Creates a quaternion and normalizes it.
     *
     * @param x
     * @param y
     * @param z
     * @param w
     */
    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        normalize();
    }

    /**
     * Normalizes the quaternion.
     */
    public void normalize() {
        float mag = (float) Math.sqrt(w * w + x * x + y * y + z * z);
        w /= mag;
        x /= mag;
        y /= mag;
        z /= mag;
    }

    /**
     * Converts the quaternion to a 4x4 matrix representing the exact same
     * rotation as this quaternion. (The rotation is only contained in the
     * top-left 3x3 part, but a 4x4 matrix is returned here for convenience
     * seeing as it will be multiplied with other 4x4 matrices).
     * <p>
     * More detailed explanation here:
     * http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToMatrix/
     *
     * @return The rotation matrix which represents the exact same rotation as
     * this quaternion.
     */
    public Matrix4f toRotationMatrix() {
        Matrix4f matrix = new Matrix4f();
        final float xy = x * y;
        final float xz = x * z;
        final float xw = x * w;
        final float yz = y * z;
        final float yw = y * w;
        final float zw = z * w;
        final float xSquared = x * x;
        final float ySquared = y * y;
        final float zSquared = z * z;
        matrix.m00(1 - 2 * (ySquared + zSquared));
        matrix.m01(2 * (xy - zw));
        matrix.m02(2 * (xz + yw));
        matrix.m03(0);
        matrix.m10(2 * (xy + zw));
        matrix.m11(1 - 2 * (xSquared + zSquared));
        matrix.m12(2 * (yz - xw));
        matrix.m13(0);
        matrix.m20(2 * (xz - yw));
        matrix.m21(2 * (yz + xw));
        matrix.m22(1 - 2 * (xSquared + ySquared));
        matrix.m23 (0);
        matrix.m30(0);
        matrix.m31(0);
        matrix.m32(0);
        matrix.m33(1);
        return matrix;
    }

    /**
     * Extracts the rotation part of a transformation matrix and converts it to
     * a quaternion using the magic of maths.
     * <p>
     * More detailed explanation here:
     * http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToQuaternion/index.htm
     *
     * @param matrix - the transformation matrix containing the rotation which this
     *               quaternion shall represent.
     */
    public static Quaternion fromMatrix(Matrix4f matrix) {
        float w, x, y, z;
        float diagonal = matrix.m00() + matrix.m11() + matrix.m22();
        if (diagonal > 0) {
            float w4 = (float) (Math.sqrt(diagonal + 1f) * 2f);
            w = w4 / 4f;
            x = (matrix.m21() - matrix.m12()) / w4;
            y = (matrix.m02() - matrix.m20()) / w4;
            z = (matrix.m10() - matrix.m01()) / w4;
        } else if ((matrix.m00() > matrix.m11()) && (matrix.m00() > matrix.m22())) {
            float x4 = (float) (Math.sqrt(1f + matrix.m00() - matrix.m11() - matrix.m22()) * 2f);
            w = (matrix.m21() - matrix.m12()) / x4;
            x = x4 / 4f;
            y = (matrix.m01() + matrix.m10()) / x4;
            z = (matrix.m02() + matrix.m20()) / x4;
        } else if (matrix.m11() > matrix.m22()) {
            float y4 = (float) (Math.sqrt(1f + matrix.m11() - matrix.m00() - matrix.m22()) * 2f);
            w = (matrix.m02() - matrix.m20()) / y4;
            x = (matrix.m01() + matrix.m10()) / y4;
            y = y4 / 4f;
            z = (matrix.m12() + matrix.m21()) / y4;
        } else {
            float z4 = (float) (Math.sqrt(1f + matrix.m22() - matrix.m00() - matrix.m11()) * 2f);
            w = (matrix.m10() - matrix.m01()) / z4;
            x = (matrix.m02() + matrix.m20()) / z4;
            y = (matrix.m12() + matrix.m21()) / z4;
            z = z4 / 4f;
        }
        return new Quaternion(x, y, z, w);
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }
}
