package me.barni;

import org.lwjgl.opengl.GL30;

import java.util.Arrays;
import java.util.Random;

public class Map {
    int[] tiles;
    int w, h;
    ShaderProgram shaderProgram;
    VertexArrayObject vao;

    public Map(int w, int h) {
        this.w = w;
        this.h = h;

        tiles = new int[w * h];

        Random r = new Random();
        for (int i = 0; i < w * h; i++) {
            tiles[i] = (int) r.nextGaussian();
        }

        shaderProgram = new ShaderProgram();
        shaderProgram.create("mapTile");
        shaderProgram.link();

        vao = new VertexArrayObject();
        float[] vArray = new float[8];
        vao.setVertexData(vArray);
        vao.setElementData(RenderableQuad.elementArray);
        vao.addAttributePointer(2); //Position (x,y)
    }

    private int[] generateElementArray()
    {
        int[] pattern = RenderableQuad.elementArray;
        int[] a = new int[pattern.length*w];

        int count = 0;
        for (int i = 0; i < w* pattern.length; i += pattern.length)
        {
            System.arraycopy(pattern, 0, a, i, pattern.length);
            for (int j = 0; j < pattern.length; j++)
            {
                a[j+i] += count;
            }
            count++;
        }
        System.out.println(Arrays.toString(a));

        return a;
    }

    final int TSIZE = 32;

    Random r = new Random();

    public void render(Camera camera, float a) {
        shaderProgram.bind();
        vao.bind(false);

        shaderProgram.uploadMat4("uProjMat", camera.getProjMat());
        shaderProgram.uploadMat4("uViewMat", camera.getViewMat());
        shaderProgram.uploadFloat("uAlpha", a);

        for (int i = 0; i < w * h; i++) {
            float[] vArray = RenderableQuad.generateVertexArray(i % w * TSIZE,
                    i / w * TSIZE, TSIZE, TSIZE);


            if (r.nextBoolean())
            {
                tiles[i] += r.nextInt(11)-5;
                if (tiles[i] < 0)
                    tiles[i] = 0;
                if (tiles[i] >= tiles.length)
                    tiles[i] = tiles.length-1;
            }


            vao.setVertexData(vArray);
            shaderProgram.uploadFloat("uTileID", tiles[i]);

            GL30.glDrawElements(GL30.GL_TRIANGLES, vao.getVertexLen(), GL30.GL_UNSIGNED_INT, 0);
        }


        shaderProgram.unBind();
        vao.unBind();
    }
}
