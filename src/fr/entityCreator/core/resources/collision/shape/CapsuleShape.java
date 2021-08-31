/*
 * This file is part of React, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2013 Flow Powered <https://flowpowered.com/>
 * Original ReactPhysics3D C++ library by Daniel Chappuis <http://danielchappuis.ch>
 * React is re-licensed with permission from ReactPhysics3D author.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.entityCreator.core.resources.collision.shape;


import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.core.resources.collision.maths.Matrix3x3;
import fr.entityCreator.core.resources.collision.maths.ReactDefaults;
import fr.entityCreator.core.resources.collision.maths.Vector3;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.toolBox.Config;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;

/**
 * Represents a capsule collision shape that is defined around the Y axis. A capsule shape can be seen as the convex hull of two spheres. The capsule shape is defined by its radius (radius of the two
 * spheres of the capsule) and its height (distance between the centers of the two spheres). This collision shape does not have an explicit object margin distance. The margin is implicitly the radius
 * and height of the shape. Therefore, there is no need to specify an object margin for a capsule shape.
 */
public class CapsuleShape extends CollisionShape {
    private float mRadius;
    private float mHalfHeight;

    /**
     * Constructs a new capsule shape from its radius and height.
     *
     * @param radius The radius
     * @param height The height
     */
    public CapsuleShape(float radius, float height) {
        super(CollisionShapeType.CAPSULE, radius, Config.CAPSULE);
        mRadius = radius;
        mHalfHeight = height * 0.5f;
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than zero");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than zero");
        }
    }

    /**
     * Copy constructor.
     *
     * @param shape The shape to copy
     */
    public CapsuleShape(CapsuleShape shape) {
        super(shape);
        mRadius = shape.mRadius;
        mHalfHeight = shape.mHalfHeight;
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public void setmHalfHeight(float mHalfHeight) {
        this.mHalfHeight = mHalfHeight;
    }

    /**
     * Returns the radius of the spherical ends of the capsule.
     *
     * @return The radius of the capsule
     */
    public float getRadius() {
        return mRadius;
    }

    /**
     * Returns the distance between the middle of the two hemispheres.
     *
     * @return The height of the capsule
     */
    public float getHeight() {
        return mHalfHeight + mHalfHeight;
    }

    @Override
    protected void createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 2;
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(createErrorPanel(true), gc);
        gc.gridy = 2;
        panel.add(createErrorPanel(false), gc);
        this.panel = panel;
    }

    @Override
    public Vector3 getLocalSupportPointWithMargin(Vector3 direction) {
        if (direction.lengthSquare() >= ReactDefaults.MACHINE_EPSILON * ReactDefaults.MACHINE_EPSILON) {
            final Vector3 unitDirection = direction.getUnit();
            final Vector3 centerTopSphere = new Vector3(0, mHalfHeight, 0);
            final Vector3 topSpherePoint = Vector3.add(centerTopSphere, Vector3.multiply(unitDirection, mRadius));
            final float dotProductTop = topSpherePoint.dot(direction);
            final Vector3 centerBottomSphere = new Vector3(0, -mHalfHeight, 0);
            final Vector3 bottomSpherePoint = Vector3.add(centerBottomSphere, Vector3.multiply(unitDirection, mRadius));
            final float dotProductBottom = bottomSpherePoint.dot(direction);
            if (dotProductTop > dotProductBottom) {
                return topSpherePoint;
            } else {
                return bottomSpherePoint;
            }
        }
        return new Vector3(0, mRadius, 0);
    }

    @Override
    public Vector3 getLocalSupportPointWithoutMargin(Vector3 direction) {
        if (direction.getY() > 0) {
            return new Vector3(0, mHalfHeight, 0);
        } else {
            return new Vector3(0, -mHalfHeight, 0);
        }
    }

    @Override
    public void getLocalBounds(Vector3 min, Vector3 max) {
        max.setX(mRadius);
        max.setY(mHalfHeight + mRadius);
        max.setZ(mRadius);
        min.setX(-mRadius);
        min.setY(-max.getY());
        min.setZ(min.getX());
    }

    @Override
    public void computeLocalInertiaTensor(Matrix3x3 tensor, float mass) {
        final float height = mHalfHeight + mHalfHeight;
        final float radiusSquare = mRadius * mRadius;
        final float heightSquare = height * height;
        final float radiusSquareDouble = radiusSquare + radiusSquare;
        final float factor1 = 2 * mRadius / (4 * mRadius + 3 * height);
        final float factor2 = 3 * height / (4 * mRadius + 3 * height);
        final float sum1 = 0.4f * radiusSquareDouble;
        final float sum2 = 0.75f * height * mRadius + 0.5f * heightSquare;
        final float sum3 = 0.25f * radiusSquare + 1f / 12 * heightSquare;
        final float IxxAndzz = factor1 * mass * (sum1 + sum2) + factor2 * mass * sum3;
        final float Iyy = factor1 * mass * sum1 + factor2 * mass * 0.25f * radiusSquareDouble;
        tensor.setAllValues(
                IxxAndzz, 0, 0,
                0, Iyy, 0,
                0, 0, IxxAndzz);
    }

    @Override
    public CollisionShape clone() {
        return new CapsuleShape(this);
    }

    @Override
    public boolean isEqualTo(CollisionShape otherCollisionShape) {
        final CapsuleShape otherShape = (CapsuleShape) otherCollisionShape;
        return mRadius == otherShape.mRadius && mHalfHeight == otherShape.mHalfHeight;
    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(String.valueOf(mRadius) + String.valueOf(mHalfHeight * 2)));
    }



    private JPanel createErrorPanel(boolean isRadius) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(isRadius ? "Rayon : " : "Hauteur : ");
        label.setFont(MainFrame.SMALL_FONT);
        panel.add(label, "West");
        JFormattedTextField field = createTextField(4);
        field.setText("1,0");
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (field.getText().isEmpty()) {
                    return;
                }
                if (isRadius) {
                    setmRadius((Float) Float.parseFloat(field.getText().replaceAll(",", "")));
                } else {
                    setmHalfHeight((Float) Float.parseFloat(field.getText().replaceAll(",", "")) / 2);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (field.getText().isEmpty()) {
                    return;
                }
                if (isRadius) {
                    setmRadius((Float) Float.parseFloat(field.getText().replaceAll(",", "")));
                } else {
                    setmHalfHeight((Float) Float.parseFloat(field.getText().replaceAll(",", "")) / 2);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (field.getText().isEmpty()) {
                    return;
                }
                if (isRadius) {
                    setmRadius((Float)Float.parseFloat(field.getText().replaceAll(",", "")));
                } else {
                    setmHalfHeight((Float) Float.parseFloat(field.getText().replaceAll(",", "")) / 2);
                }
            }
        });
        panel.add(field, "East");
        return panel;
    }


    private JFormattedTextField createTextField(int columns) {
        NumberFormat floatFormat = NumberFormat.getNumberInstance();
        floatFormat.setMinimumFractionDigits(1);
        floatFormat.setMaximumFractionDigits(5);
        NumberFormatter numberFormatter = new NumberFormatter(floatFormat);
        numberFormatter.setValueClass(Float.class);
        numberFormatter.setAllowsInvalid(false);
        //numberFormatter.setMinimum(0);
        JFormattedTextField text = new JFormattedTextField(numberFormatter);
        text.setColumns(columns);
        text.setFont(MainFrame.SMALL_FONT);
        text.setHorizontalAlignment(0);
        return text;
    }

    public String toString() {
        return "Capsule";
    }
}
