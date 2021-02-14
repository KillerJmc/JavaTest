package com.test.algorithm.tree.interfaces;

import com.test.algorithm.list.sequence.impl.SequenceQueue;

public interface TreeIterator<K> {
    SequenceQueue<K> preErgodic();
    SequenceQueue<K> midErgodic();
    SequenceQueue<K> afterErgodic();
    SequenceQueue<K> laserErgodic();
}
