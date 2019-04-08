import sys
import heapq
from math import ceil, floor

def answer(N, votes):
    plus_ones = set()
    for i in xrange(0, N):
        if (i * 1000.0 / N) % 10 >= 5:
            plus_ones.add(i)
    deltas = {x: N + 1 for x in xrange(0, N + 1)}
    curr = None
    for i in xrange(N - 1, -1, -1):
        if i in plus_ones:
            curr = i
        elif curr is not None:
            deltas[i] = curr - i

    ideal_votes = [0] * N
    for i in xrange(len(votes)):
        ideal_votes[i] = votes[i]

    heap = []
    for v in ideal_votes:
        heapq.heappush(heap, (deltas[v], v))

    for i in xrange(N - sum(votes)):
        _, new_votes = heapq.heappop(heap)
        heapq.heappush(heap, (deltas[new_votes + 1], new_votes + 1))

    total = 0
    for _, vote_num in heap:
        if (vote_num * 1000 / N) % 10 >= 5:
            total += int(ceil(vote_num * 100.0 / N))
        else:
            total += int(floor(vote_num * 100.0 / N))
    return total


num_cases = int(sys.stdin.readline())
for case in xrange(1, num_cases + 1):
    N, _ = [int(x) for x in sys.stdin.readline().split()]
    votes = [int(x) for x in sys.stdin.readline().split()]
    val = answer(N, votes)
    print 'Case #{}: {}'.format(case, val)