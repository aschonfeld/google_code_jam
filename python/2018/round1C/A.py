from collections import defaultdict


def run_test():
    N, L = map(int, raw_input().split())
    if L == 1:
        map(lambda _: raw_input(), range(N))
        return '-'
    words = set()
    characters = defaultdict(list)
    for _ in range(N):
        word = raw_input()
        words.add(word)
        for idx, c in enumerate(word):
            characters[idx].append(c)

    curr_i = 1
    new_words = characters[0]
    while curr_i < L:
        curr_new_words = []
        for nw in new_words:
            for c in characters[curr_i]:
                new_word = nw + c
                if curr_i == L - 1 and new_word not in words:
                    return new_word
                else:
                    if new_word not in curr_new_words:
                        curr_new_words.append(new_word)
        curr_i += 1
        new_words = curr_new_words

    return '-'

T = int(raw_input())
for i in range(1, T + 1):
    output = run_test()
    print("Case #{}: {}".format(i, output))
