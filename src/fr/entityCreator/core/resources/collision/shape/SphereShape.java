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
import fr.entityCreator.core.resources.collision.maths.Transform;
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
 * Represents a sphere collision shape that is centered at the origin and defined by its radius. This collision shape does not have an explicit object margin distance. The margin is implicitly the
 * radius of the sphere. Therefore, there is no need to specify an object margin for a sphere shape.
 */
public class SphereShape extends CollisionShape {
    private float mRadius;

    /**
     * Constructs a new sphere from the radius.
     *
     * @param radius The radius
     */
    public SphereShape(float radius) {
        super(CollisionShapeType.SPHERE, radius, Config.SPHERE);
        mRadius = radius;
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than zero");
        }
    }

    /**
     * Copy constructor.
     *
     * @param shape The shape to copy
     */
    public SphereShape(SphereShape shape) {
        super(shape);
        mRadius = shape.mRadius;
    }

    /**
     * Gets the radius.
     *
     * @return The radius
     */
    public float getRadius() {
        return mRadius;
    }

    @Override
    protected void createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 2;
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(createErrorPanel(), gc);
        this.panel = panel;
    }

    @Override
    public Vector3 getLocalSupportPointWithMargin(Vector3 direction) {
        if (direction.lengthSquare() >= ReactDefaults.MACHINE_EPSILON * ReactDefaults.MACHINE_EPSILON) {
            return Vector3.multiply(mMargin, direction.getUnit());
        }
        return new Vector3(0, mMargin, 0);
    }

    @Override
    public Vector3 getLocalSupportPointWithoutMargin(Vector3 direction) {
        return new Vector3(0, 0, 0);
    }

    @Override
    public void getLocalBounds(Vector3 min, Vector3 max) {
        max.setX(mRadius);
        max.setY(mRadius);
        max.setZ(mRadius);
        min.setX(-mRadius);
        min.setY(min.getX());
        min.setZ(min.getX());
    }

    @Override
    public void computeLocalInertiaTensor(Matrix3x3 tensor, float mass) {
        final float diag = 0.4f * mass * mRadius * mRadius;
        tensor.setAllValues(
                diag, 0, 0,
                0, diag, 0,
                0, 0, diag);
    }

    @Override
    public void updateAABB(AABB aabb, Transform transform) {
        final Vector3 extents = new Vector3(mRadius, mRadius, mRadius);
        aabb.setMin(Vector3.subtract(transform.getPosition(), extents));
        aabb.setMax(Vector3.add(transform.getPosition(), extents));
    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(String.valueOf(mRadius)));
    }


    private JPanel createErrorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Rayon : ");
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
                mRadius = ((Float) Float.parseFloat(field.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (field.getText().isEmpty()) {
                    return;
                }
                mRadius = ((Float) Float.parseFloat(field.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (field.getText().isEmpty()) {
                    return;
                }
                mRadius = ((Float) Float.parseFloat(field.getText()));
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

    @Override
    public SphereShape clone() {
        return new SphereShape(this);
    }

    @Override
    public boolean isEqualTo(CollisionShape otherCollisionShape) {
        final SphereShape otherShape = (SphereShape) otherCollisionShape;
        return mRadius == otherShape.mRadius;
    }

    public String toString() {
        return "Sphere";
    }
}
