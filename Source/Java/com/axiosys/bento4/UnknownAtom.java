/*****************************************************************
|
|    Copyright 2002-2008 Axiomatic Systems, LLC
|
|    $Id: UnknownAtom.java 196 2008-10-14 22:59:31Z bok $
|
|    This file is part of Bento4/AP4 (MP4 Atom Processing Library).
|
|    Unless you have obtained Bento4 under a difference license,
|    this version of Bento4 is Bento4|GPL.
|    Bento4|GPL is free software; you can redistribute it and/or modify
|    it under the terms of the GNU General Public License as published by
|    the Free Software Foundation; either version 2, or (at your option)
|    any later version.
|
|    Bento4|GPL is distributed in the hope that it will be useful,
|    but WITHOUT ANY WARRANTY; without even the implied warranty of
|    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
|    GNU General Public License for more details.
|
|    You should have received a copy of the GNU General Public License
|    along with Bento4|GPL; see the file COPYING.  If not, write to the
|    Free Software Foundation, 59 Temple Place - Suite 330, Boston, MA
|    02111-1307, USA.
|
****************************************************************/

package com.axiosys.bento4;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UnknownAtom extends Atom {

    private final RandomAccessFile source;
    private final long             sourceOffset;
    
    public UnknownAtom(int type, int size, RandomAccessFile source, long sourceOffset) {
        super(type, size, false);
        this.source        = source;
        this.sourceOffset = sourceOffset;
    }
    
    public UnknownAtom(int type, int size, RandomAccessFile source) throws IOException {
        this(type, size, source, source.getFilePointer());
    }
    
    public RandomAccessFile getSource()       { return source; }
    public long             getSourceOffset() { return sourceOffset; }

    protected void writeFields(DataOutputStream stream) throws IOException {
        // position into the source
        source.seek(sourceOffset);
        
        // read/write using a buffer
        int read;
        byte[] buffer = new byte[4096];        
        while ((read = source.read(buffer)) != -1) {
            stream.write(buffer, 0, read);
        }
    }
}
