package me.barni;

public class RenderableQuad {

    public float[] vertexArray = new float[8];
    public RenderableQuad(float x, float y, float w, float h) {
        //x = remap(x, 0, 1920, -1, 1);
        //y = remap(y, 0, 1080, -1, 1);
        //w = remap(w, 0, 1920, -1, 1);
        //h = remap(h, 0, 1080, -1, 1);

        //  X--------o
        //  |        |
        //  |        |
        //  o--------o
        vertexArray[0] = x;    //TL x
        vertexArray[1] = y;   //TL y

        //  o--------0
        //  |        |
        //  |        |
        //  o--------X
        vertexArray[2] = x+w;  //BR x
        vertexArray[3] = y+h;  //BR y

        //  o--------X
        //  |        |
        //  |        |
        //  o--------o
        vertexArray[4] = x+w;  //TR x
        vertexArray[5] = y;    //TR y

        //  o--------o
        //  |        |
        //  |        |
        //  X--------o
        vertexArray[6] = x;    //BL x
        vertexArray[7] = y+h;  //BL y
    }



    public static final int[] elementArray = {2,1,0,0,1,3};

    public static float[] generateVertexArray(float x, float y, float w, float h)
    {
        float[] va = new float[8];
        va[0] = x;    //TL x
        va[1] = y;    //TL y

        va[2] = x+w;  //BR x
        va[3] = y+h;  //BR y

        va[4] = x+w;  //TR x
        va[5] = y;    //TR y

        va[6] = x;    //BL x
        va[7] = y+h;  //BL y
        return va;
    }
}
