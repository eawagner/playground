package abbot.common.util.io;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;


/**
 * Utility class to allow for the management of writing a {@link java.io.OutputStream} data to a destination in chunks.
 *
 */
public abstract class AbstractBufferedOutputStream extends OutputStream {

    private byte[] buf;
    private int curr = 0;
    private boolean closed = false;

    public AbstractBufferedOutputStream(int bufferSize) {
        buf = new byte[bufferSize];
    }

    /**
     * Writes the buffer to the destination after it is either full, or has been flushed.
     * @param buf data to write
     * @throws IOException
     */
    protected abstract void writeBuffer(byte [] buf) throws IOException;

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {
        checkClosed();
        if (available() < 1)
            flush();

        buf[curr++] = (byte)b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException {
        if (curr > 0) {
            writeBuffer(Arrays.copyOf(buf, curr));
            curr = 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        if (!closed) {
            closed = true;
            flush();
            super.close();
        }
    }

    /**
     * Provides the amount of buffer that is left to be written to.
     * @return number of bytes available in the buffer
     */
    protected int available() {
        return buf.length - curr;
    }

    /**
     * Check if the stream has been closed.  If it has, it will throw an {@link java.io.IOException}.
     * @throws IOException
     */
    private void checkClosed() throws IOException {
        if (closed)
            throw new IOException("Cannot read from stream anymore.  It has been closed");
    }
}