package com.fedorzaplatin.lunolet.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud extends Stage {

    private Label velocity;
    private Label altitude;
    private Label fuelMass;
    private float lunarModuleHeight;


    /**
     * Game screen's HUD
     * @param viewport
     * @param font BitmapFont which wil be used to create labels
     * @param lunarModuleHeight
     */
    public Hud(Viewport viewport, BitmapFont font, float lunarModuleHeight) {
        super(viewport);

        this.lunarModuleHeight = lunarModuleHeight;

        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        velocity = new Label("0", style);
        altitude = new Label("0", style);
        fuelMass = new Label("0", style);

        Table table = new Table();
        table.left().top();
        table.pad(10);
        table.setFillParent(true);
        table.add(velocity).align(Align.left).pad(2);
        table.row();
        table.add(altitude).align(Align.left).pad(4);
        table.row();
        table.add(fuelMass).align(Align.left).pad(4);

        addActor(table);
    }

    public void act(Vector2 velocity, Vector2 altitude, float fuel) {
        this.velocity.setText(String.format("Velocity:  %02.2f   m/s", velocity.len()));
        this.altitude.setText(String.format("Alt.:  %03.0f   m", altitude.y - lunarModuleHeight));
        this.fuelMass.setText(String.format("Fuel:  %04.0f   kg", fuel));
        super.act();
    }
}
