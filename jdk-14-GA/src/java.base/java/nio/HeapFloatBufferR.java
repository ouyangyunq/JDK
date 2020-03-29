/*
 * Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;

import java.util.Objects;
import jdk.internal.access.foreign.MemorySegmentProxy;

/**



 * A read-only HeapFloatBuffer.  This class extends the corresponding
 * read/write class, overriding the mutation methods to throw a {@link
 * ReadOnlyBufferException} and overriding the view-buffer methods to return an
 * instance of this class rather than of the superclass.

 */

class HeapFloatBufferR
    extends HeapFloatBuffer
{
    // Cached array base offset
    private static final long ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(float[].class);

    // Cached array base offset
    private static final long ARRAY_INDEX_SCALE = UNSAFE.arrayIndexScale(float[].class);

    // For speed these fields are actually declared in X-Buffer;
    // these declarations are here as documentation
    /*




    */

    HeapFloatBufferR(int cap, int lim, MemorySegmentProxy segment) {            // package-private








        super(cap, lim, segment);
        this.isReadOnly = true;

    }

    HeapFloatBufferR(float[] buf, int off, int len, MemorySegmentProxy segment) { // package-private








        super(buf, off, len, segment);
        this.isReadOnly = true;

    }

    protected HeapFloatBufferR(float[] buf,
                                   int mark, int pos, int lim, int cap,
                                   int off, MemorySegmentProxy segment)
    {








        super(buf, mark, pos, lim, cap, off, segment);
        this.isReadOnly = true;

    }

    public FloatBuffer slice() {
        int rem = this.remaining();
        return new HeapFloatBufferR(hb,
                                        -1,
                                        0,
                                        rem,
                                        rem,
                                        this.position() + offset, segment);
    }

    @Override
    public FloatBuffer slice(int index, int length) {
        Objects.checkFromIndexSize(index, length, limit());
        return new HeapFloatBufferR(hb,
                                        -1,
                                        0,
                                        length,
                                        length,
                                        index + offset, segment);
    }

    public FloatBuffer duplicate() {
        return new HeapFloatBufferR(hb,
                                        this.markValue(),
                                        this.position(),
                                        this.limit(),
                                        this.capacity(),
                                        offset, segment);
    }

    public FloatBuffer asReadOnlyBuffer() {








        return duplicate();

    }






















































    public boolean isReadOnly() {
        return true;
    }

    public FloatBuffer put(float x) {





        throw new ReadOnlyBufferException();

    }

    public FloatBuffer put(int i, float x) {





        throw new ReadOnlyBufferException();

    }

    public FloatBuffer put(float[] src, int offset, int length) {










        throw new ReadOnlyBufferException();

    }

    public FloatBuffer put(FloatBuffer src) {



























        throw new ReadOnlyBufferException();

    }

    public FloatBuffer put(int index, float[] src, int offset, int length) {







        throw new ReadOnlyBufferException();

    }





















    public FloatBuffer compact() {









        throw new ReadOnlyBufferException();

    }








































































































































































































































































































































































































    public ByteOrder order() {
        return ByteOrder.nativeOrder();
    }







}