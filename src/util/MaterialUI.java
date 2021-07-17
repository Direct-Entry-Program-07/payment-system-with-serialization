/*
 * Copyright (c) 2021  Dinusha Jayawardena. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 */

package util;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MaterialUI {

    public static void paintTextFields(TextField... textFields) {
        for (TextField txt : textFields) {

            AnchorPane pneTextContainer = (AnchorPane) txt.getParent();
            String floatedText = txt.getAccessibleText();
            Canvas canvas = new Canvas(pneTextContainer.getPrefWidth(), pneTextContainer.getPrefHeight());
            GraphicsContext ctx = canvas.getGraphicsContext2D();

            pneTextContainer.getChildren().add(0, canvas);
            pneTextContainer.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                canvas.setWidth(newValue.getWidth());
                canvas.setHeight(newValue.getHeight());
                redrawTextFieldCanvas(canvas, ctx, floatedText, false);
            });

            txt.focusedProperty().addListener((observable, oldValue, newValue) -> {
                redrawTextFieldCanvas(canvas, ctx, floatedText, newValue);
            });

            pneTextContainer.setOnMouseClicked(event -> {
                txt.requestFocus();
            });
        }
    }

    private static void redrawTextFieldCanvas(Canvas canvas, GraphicsContext ctx, String floatedText,boolean focus ){

        ctx.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        ctx.setStroke(focus ? Color.valueOf("#03A9F4"): Color.rgb(0,0,0,0.30));
        //ctx.setLineWidth(5); //change the width of the line
        ctx.strokeRoundRect(2,4,canvas.getWidth() - 4, canvas.getHeight() - 4, 10, 10);
        ctx.setFill(Color.WHITE);
        ctx.fillRect(10,0, new Text(floatedText).getLayoutBounds().getWidth() + 10,20);
        ctx.setFill(focus? Color.valueOf("#03A9F4") : Color.rgb(0,0,0, 0.60));
        ctx.fillText(floatedText, 15,10);

    }
}
