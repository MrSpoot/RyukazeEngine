package ryukazev2.graphics;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
@AllArgsConstructor
public class Image {

    private ByteBuffer byteBuffer;
    private int width;
    private int height;

}
