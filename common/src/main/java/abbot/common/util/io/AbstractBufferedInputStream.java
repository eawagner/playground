package abbot.common.util.io;

import java.io.IOException;
import java.io.InputStream;


/**
 * Utility class to allow for the source of the stream data to control the buffer size.  Useful for when the source of
 * of the stream data can be provided in chunks that can be reassembled into a contiguous {@link java.io.InputStream)
 */
public abstract class AbstractBufferedInputStream extends InputStream {

    private static final int EOF = -1;
    private byte [] buf = null;
    private int curr = 0;
    private boolean closed = false;

    /**
     * Returns true if there is no more source data.
     * @return true if at the end of the source data.
     */
    protected abstract boolean isEOF();

    /**
     * Retrieves the next set of data from the source.
     * @return A byte array containing the next set of data.
     * @throws IOException
     */
    protected abstract byte [] getNextBuffer() throws IOException;

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException {
        while (available() < 1) {
            if (isEOF())
                return EOF;
            else
                loadBuffer();
        }

        return buf[curr++] & 0xff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int available() throws IOException {
        checkClosed();
        if (buf == null)
            return 0;

        return buf.length - curr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        if (!closed) {
            closed = true;
            super.close();
        }
    }

    /**
     * Retrieves the next set of data and resets the current index.
     * @throws IOException
     */
    private void loadBuffer() throws IOException {
        buf = getNextBuffer();
        curr = 0;
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
