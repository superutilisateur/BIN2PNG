import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

/**
 * This program embed binary (exe, zip, ...) to png image.
 * Since some email providers don't want us to share data they don't understand
 * this is a simple way to let there bots "think" it's just a picture.
 * build: javac BIN2PNG.java
 * usage: java BIN2PNG <encode/decode> filename 
 * (theoretical max file size: 2GB)
 * 
 * Copyright (c) 2023 Mikaël GUILLEMOT
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
public class BIN2PNG {
	public static void main(String[] args) throws IOException {
		int lenSize = Integer.BYTES;
		if (args.length == 2)
			if (args[0].equalsIgnoreCase("encode")) {
				String in = args[1];
				File fileIn = new File(in);
				File fileOut = new File(in + ".png");
				System.out.println("encoding " + fileIn.getName() + " to " + fileOut.getName());
				Path path = Paths.get(in);
				byte[] data = Files.readAllBytes(path);
				int len = data.length;
				int rez = (int) (Math.sqrt(len + lenSize) + 1);
				System.out.println("encoding " + len +" bytes");
				BufferedImage out = new BufferedImage(rez, rez, BufferedImage.TYPE_BYTE_GRAY);
				byte[] bufferHolder = ((DataBufferByte) out.getRaster().getDataBuffer()).getData();
				System.arraycopy(data, 0, bufferHolder, lenSize, data.length);
				System.arraycopy(intToBytes(len), 0, bufferHolder, 0, lenSize);
				ImageIO.write(out, "png", fileOut);
				System.out.println("encoding done");
				System.exit(0);
			} else if (args[0].equalsIgnoreCase("decode")) {
				String in = args[1];
				File fileIn = new File(in);
				String fileInName = fileIn.getName();
				File fileOut = new File(fileInName.substring(0, fileInName.lastIndexOf(".")));
				System.out.println("decoding " + fileInName + " to " + fileOut);
				byte[] data = ((DataBufferByte) ImageIO.read(fileIn).getRaster().getDataBuffer()).getData();
				byte[] lenArray = new byte[lenSize];
				System.arraycopy(data, 0, lenArray, 0, lenSize);
				int len = bytesToInt(lenArray);
				System.out.println("decoding " + len + " bytes");
				FileOutputStream outputStream = new FileOutputStream(fileOut);
				outputStream.write(data, lenSize, (int) len);
				outputStream.close();
				System.out.println("decoding done ");
				System.exit(0);
			}
		System.out.println("usage \"BIN2PNG encode/decode filename (max 2Go)\"");
	}

	public static byte[] intToBytes(int i) {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(i);
		return buffer.array();
	}

	public static int bytesToInt(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		return buffer.getInt();
	}
}
