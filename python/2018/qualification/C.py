import sys
import random


def find_dimensions(A):
    w = A ** .5
    rect = w % 1 > 0
    w = int(w)
    if rect:
        return w, w + 1
    return w, w


def run_test(A):
    l, w = find_dimensions(A)
    I = J = 500
    points_needed = []
    for i in range(-(l/2), l-(l/2))[1:-1]:
        for j in range(-(w/2), w-(w/2))[1:-1]:
            points_needed.append('{} {}'.format(500 + i, 500 + j))
    cell = '{} {}'.format(I, J)
    print cell
    sys.stdout.flush()
    while not I == J == -1 and not I == J == 0:
        I, J = map(int, raw_input().split())
        if I == J == -1 or I == J == 0:
            return

        cell = points_needed[random.randint(0, len(points_needed)-1)]
        print cell
        sys.stdout.flush()


T = int(raw_input())
for t in range(1, T+1):
    run_test(int(raw_input()))
