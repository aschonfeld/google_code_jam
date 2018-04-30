def score_pct(N):
    def _score_pct(selections):
        pct = (100.0 * selections) / N
        if (pct % 1) * 10 >= 5:
            return int(pct) + 1
        return int(pct)
    return _score_pct


def find_good_count(L_counts, remaining_responses, best_counts):
    for i, lc in enumerate(L_counts):
        if lc in best_counts:
            continue
        good_count = next((x for x in best_counts if lc + x <= remaining_responses), None)
        if good_count is not None:
            L_counts[i] = good_count + lc
            remaining_responses -= good_count
            return remaining_responses, L_counts
    good_count = next((x for x in best_counts if x <= remaining_responses), None)
    if good_count is not None:
        L_counts.append(good_count)
        remaining_responses -= good_count
        return remaining_responses, L_counts
    for i, lc in enumerate(L_counts):
        if lc in best_counts:
            continue
        L_counts[i] += 1
        remaining_responses -= 1
        return remaining_responses, L_counts
    L_counts.append(1)
    remaining_responses -= 1
    return remaining_responses, L_counts


def _run_test(N, L, L_counts):
    responses = sum(L_counts)
    remaining_responses = N - responses
    best_counts = map(lambda (x, y): x, sorted(filter(lambda (x, y): (y >= .5), map(lambda x: (x, ((x * 100.0) / N) % 1), range(1, remaining_responses + 1))), key=lambda (x, y): 1 - y, reverse=True))
    while remaining_responses > 0:
        remaining_responses, L_counts = find_good_count(L_counts, remaining_responses, best_counts)
    return sum(map(score_pct(N), L_counts))


def run_test():
    N, L = map(int, raw_input().split())
    L_counts = map(int, raw_input().split())
    return _run_test(N, L, L_counts)


T = int(raw_input())
for i in range(1, T + 1):
    print("Case #{}: {}".format(i, run_test()))


def sum_to_n(n, size, limit=None):
    """Produce all lists of `size` positive integers in decreasing order
    that add up to `n`."""
    if size == 1:
        yield [n]
        return
    if limit is None:
        limit = n
    start = (n + size - 1) // size
    stop = min(limit, n - size + 1) + 1
    for i in range(start, stop):
        for tail in sum_to_n(n - i, size - 1, i):
            yield [i] + tail

for N in range(2, 26):
    for L in range(1, N + 1):
        for k in range(1, N + 1):
            print 'N: {}, L: {}, k: {}'.format(N, L, k)
            for p in sum_to_n(N, L):
                print N, len(p), p
                print _run_test(N, len(p), p)