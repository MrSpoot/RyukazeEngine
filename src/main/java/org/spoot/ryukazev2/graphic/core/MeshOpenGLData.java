package org.spoot.ryukazev2.graphic.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.spoot.ryukazev2.graphic.graphics.Material;

@Data
@NoArgsConstructor
public class MeshOpenGLData {

    private int vao;
    private int vbo;
    private Material material;

}
