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
package com.flowpowered.react.collision.shape;


import com.flowpowered.react.*;
import com.flowpowered.react.math.*;
import fr.entityCreator.core.exporter.*;
import fr.entityCreator.frame.*;
import fr.entityCreator.toolBox.*;
import org.joml.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.nio.channels.*;
import java.text.*;
import java.lang.Math;

/**
 * Represents a cylinder collision shape around the Y axis and centered at the origin. The cylinder is defined by its height and the radius of its base. The "transform" of the corresponding rigid body
 * gives an orientation and a position to the cylinder. This collision shape uses an extra margin distance around it for collision detection purpose. The default margin is 4cm (if your units are
 * meters, which is recommended). In case, you want to simulate small objects (smaller than the margin distance), you might want to reduce the margin by specifying your own margin distance using the
 * "margin" parameter in the constructor of the cylinder shape. Otherwise, it is recommended to use the default margin distance by not using the "margin" parameter in the constructor.
 */
public class CylinderShape extends CollisionShape {
    private float mRadius;
    private float mHalfHeight;

    /**
     * Constructs a new cylinder from the radius of the base and the height.
     *
     * @param radius The radius of the base
     * @param height The height
     */
    public CylinderShape(float radius, float height) {
        this(radius, height, ReactDefaults.OBJECT_MARGIN);
    }

    /**
     * Constructs a new cylinder from the radius of the base and the height and the AABB margin.
     *
     * @param radius The radius of the base
     * @param height The height
     * @param margin The margin
     */
    public CylinderShape(float radius, float height, float margin) {
        super(CollisionShapeType.CYLINDER, margin, Config.CYLINDER);
        mRadius = radius;
        mHalfHeight = height / 2;
        if (mRadius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than zero");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than zero");
        }
        if (margin <= 0) {
            throw new IllegalArgumentException("Margin must be greater than 0");
        }
        createPanel();
    }

    /**
     * Copy constructor.
     *
     * @param shape The shape to copy
     */
    public CylinderShape(CylinderShape shape) {
        super(shape);
        mRadius = shape.mRadius;
        mHalfHeight = shape.mHalfHeight;
        createPanel();
    }

    public static CylinderShape load(String content) {
        String[] values = content.split(";");
        float radius = Float.parseFloat(values[0]);
        float height = Float.parseFloat(values[1]);
        return new CylinderShape(radius, height);
    }

    /**
     * Gets the radius of the base.
     *
     * @return The radius
     */
    public float getRadius() {
        return mRadius;
    }

    /**
     * Gets the height of the cylinder.
     *
     * @return The height
     */
    public float getHeight() {
        return mHalfHeight + mHalfHeight;
    }

    @Override
    public Vector3f applyCorrection() {
        return new Vector3f(0,mHalfHeight,0);
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
        this.getRelativeTransform().getScale().set(mRadius, mHalfHeight*2, mRadius);
        this.panel = panel;
    }

    @Override
    public void setScale() {
        this.getRelativeTransform().getScale().set(mRadius, mHalfHeight*2, mRadius);
    }

    @Override
    public Vector3 getLocalSupportPointWithMargin(Vector3 direction) {
        final Vector3 supportPoint = getLocalSupportPointWithoutMargin(direction);
        final Vector3 unitVec;
        if (direction.lengthSquare() > ReactDefaults.MACHINE_EPSILON * ReactDefaults.MACHINE_EPSILON) {
            unitVec = direction.getUnit();
        } else {
            unitVec = new Vector3(0, 1, 0);
        }
        supportPoint.add(Vector3.multiply(unitVec, mMargin));
        return supportPoint;
    }

    @Override
    public Vector3 getLocalSupportPointWithoutMargin(Vector3 direction) {
        final Vector3 supportPoint = new Vector3(0, 0, 0);
        final float uDotv = direction.getY();
        final Vector3 w = new Vector3(direction.getX(), 0, direction.getZ());
        final float lengthW = (float) Math.sqrt(direction.getX() * direction.getX() + direction.getZ() * direction.getZ());
        if (lengthW > ReactDefaults.MACHINE_EPSILON) {
            if (uDotv < 0.0) {
                supportPoint.setY(-mHalfHeight);
            } else {
                supportPoint.setY(mHalfHeight);
            }
            supportPoint.add(Vector3.multiply(mRadius / lengthW, w));
        } else {
            if (uDotv < 0.0) {
                supportPoint.setY(-mHalfHeight);
            } else {
                supportPoint.setY(mHalfHeight);
            }
        }
        return supportPoint;
    }

    @Override
    public void getLocalBounds(Vector3 min, Vector3 max) {
        max.setX(mRadius + mMargin);
        max.setY(mHalfHeight + mMargin);
        max.setZ(max.getX());
        min.setX(-max.getX());
        min.setY(-max.getY());
        min.setZ(min.getX());
    }

    @Override
    public void computeLocalInertiaTensor(Matrix3x3 tensor, float mass) {
        final float height = 2 * mHalfHeight;
        final float diag = (1f / 12) * mass * (3 * mRadius * mRadius + height * height);
        tensor.setAllValues(
                diag, 0, 0,
                0, 0.5f * mass * mRadius * mRadius, 0,
                0, 0, diag);
    }

    @Override
    public CylinderShape clone() {
        return new CylinderShape(this);
    }



    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(String.valueOf(mRadius) + ";" + String.valueOf(mHalfHeight * 2) + "\n"));
    }


    @Override
    public void scale(Vector3f scale) {
        mRadius *= scale.x();
        float tHeight = mHalfHeight * 2.1f;
        tHeight *= scale.y();
        mHalfHeight = tHeight / 2;
    }

    private JPanel createErrorPanel(boolean isRadius) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(isRadius ? "Rayon : " : "Hauteur : ");
        label.setFont(MainFrame.SMALL_FONT);
        panel.add(label, "West");
        JFormattedTextField field = createTextField(4);
        field.setValue(isRadius? mRadius : mHalfHeight*2);
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void warn(){
                if (field.getText().isEmpty()) {
                    return;
                }
                if (isRadius) {
                    mRadius = ((Float) Float.parseFloat(field.getText().replaceAll(",",".")));
                } else {
                    mHalfHeight = ((Float) Float.parseFloat(field.getText().replaceAll(",",".")) / 2);
                }
                CylinderShape.this.getRelativeTransform().getScale().set(mRadius, mHalfHeight*2, mRadius);
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
        return "Cylindre";
    }
}
