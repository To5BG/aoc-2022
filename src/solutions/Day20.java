package solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day20 {
    public static Object preprocess(String input, int star) {
        star = Math.min(Math.max(star, 1), 2);
        List<Long> nums = input.lines().map(Long::parseLong).toList();
        return star == 1 ? solveStar1(nums) : solveStar2(nums);
    }
    public static Object solveStar1(List<Long> input) {
        return solve(input, 1, 1);
    }
    public static Object solveStar2(List<Long> input) {
        return solve(input, 811589153, 10);
    }
    public static Object solve(List<Long> input, int key, int reps) {
        List<Node> nodes = new ArrayList<>();
        Node head = new Node(); Node zero = null;
        nodes.add(head);

        long e = input.get(0) * key;
        head.normalizedElement = e;
        head.originalElement = e;
        if (head.normalizedElement == 0) zero = head;
        Node curr = head;
        for (int i = 1; i < input.size(); i++) {
            curr.next = new Node();
            nodes.add(curr.next);
            curr.next.prev = curr;
            e = input.get(i) * key;
            curr.next.normalizedElement = e;
            curr.next.originalElement = e;
            if (curr.next.normalizedElement == 0) zero = curr.next;
            curr = curr.next;
        }
        curr.next = head; head.prev = curr;
        long size = nodes.size();
        for (Node node : nodes) {
            node.normalizedElement %= (size - 1);
            if (node.normalizedElement < 0) node.normalizedElement += (size - 1);
        }
        for (int t = 0; t < reps; t++) for (Node node : nodes) node.advance(node.normalizedElement);
        return Stream.iterate(zero.at(1000), n -> n.at(1000)).limit(3)
                .mapToLong(x -> x.originalElement).sum();
    }

    static class Node {
        long originalElement;
        long normalizedElement;
        Node prev;
        Node next;
        void advance(long hops) {
            if (hops == 0) return;
            Node x = at(hops); Node y = x.next;
            prev.next = next; next.prev = prev;
            x.next = this; this.prev = x;
            this.next = y; y.prev = this;
        }
        Node at(long hops) {
            Node curr = this;
            for (long i = 0; i < hops; i++) curr = curr.next;
            return curr;
        }
    }
}
