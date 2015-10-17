/*
 * Copyright (c) 2012, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * This code originally by Tuomo Saarni.  Obtained from:
 *
 *     http://users.utu.fi/~tuiisa/Java/index.html
 *
 * under the following license:
 *
 *     Here's some java sources I've made. Most codes are free to
 *     download. If you use some of my sources just remember give me
 *     the credits.
 */

package tests.rbtree;

/**
 * A <code>Node</code> object is a node of search tree
 * including key data and satellite object.
 * <p/>
 * It can be used with binary search tree as well as with
 * red black tree or with any other search tree.
 *
 * @author Tuomo Saarni
 * @version 1.2, 08/16/01
 */

public class Node {
    /**
     * The key of the node.
     *
     * @see #key()
     * @see #keyTo
     */
    protected int key_;

    /**
     * The satellite data in the node.
     *
     * @see #object()
     * @see #objectTo
     */
    protected Object data;                // Refers to the satellite data

    /**
     * Constructs a new node. The satellite data is set to <code>null>/code>.
     *
     * @param _key The key of the node.
     */
    public Node(int _key) {
        key_ = _key;
        data = null;
    }

    /**
     * Constructs a new node.
     *
     * @param _key The key of the node.
     * @param dat  The satellite data of the node, type <code>Object</code>.
     */
    public Node(int _key, Object dat) {
        this(_key);
        data = dat;
    }

    /**
     * Returns the key of the node.
     *
     * @return The key of the node.
     */
    public int key() {
        return this.key_;
    }

    /**
     * Returns the satellite data of the node.
     *
     * @return The satellite object of the node.
     */
    public Object object() {
        return this.data;
    }

    /**
     * Returns the node.
     *
     * @return The node as a <code>String</code>.
     */
    public String toString() {
        return new String("Key: " + this.key_);
    }

    /**
     * Sets the key to _key.
     *
     * @param _key The new key of the node.
     */
    public void keyTo(int _key) {
        this.key_ = _key;
    }

    /**
     * Sets the data to o.
     *
     * @param o The new data of the node.
     */
  public void objectTo(Object o)
  {
    this.data = o;
  }

} // End class Node
