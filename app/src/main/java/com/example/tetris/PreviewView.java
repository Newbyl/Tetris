package com.example.tetris;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class PreviewView extends GLSurfaceView {

    Grille prev = new Grille(3, 4, 0);

    PreviewRenderer previewRenderer;

    public PreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Création d'un context OpenGLES 3.0
        setEGLContextClientVersion(3);

        previewRenderer = new PreviewRenderer();

        previewRenderer.setPrev(prev);
        setRenderer(previewRenderer);
        // Option pour indiquer qu'on redessine uniquement si les données changent
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        requestRender();
    }

    public void anim(Tetromino t) {
        previewRenderer.setTetromino(t);
        requestRender();
    }
}
