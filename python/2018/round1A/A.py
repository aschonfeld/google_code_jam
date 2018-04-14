from itertools import combinations


def count_chips(waffle):
    return sum(map(sum, waffle))


def check_horizontal(waffle, H, V):
    R = len(waffle)
    C = len(waffle[0])
    if H > 0:
        for h_slices in combinations(range(1, R), H):
            hl_slices = [0] + list(h_slices) + [R]
            slices = [waffle[start:finish] for start, finish in zip(hl_slices[:-1], hl_slices[1:])]
            chips_in_each_slice = map(count_chips, slices)
            if len(set(chips_in_each_slice)) == 1:
                if V > 0:
                    return check_vertical(slices, V)
                else:
                    return True
        return False
    else:
        return check_vertical(waffle, V)


def check_vertical(waffle_slices, V):
    C = len(waffle_slices[0][0])
    for v_slices in combinations(range(1, C), V):
        if all(check_vertical_slice(waffle, v_slices) for waffle in waffle_slices):
            return True
    return False


def check_vertical_slice(waffle, v_slices):
    C = len(waffle[0])
    vl_slices = [0] + list(v_slices) + [C]
    slices = [[row[start:finish] for row in waffle] for start, finish in zip(vl_slices[:-1], vl_slices[1:])]
    chips_in_each_slice = map(count_chips, slices)
    return len(set(chips_in_each_slice)) == 1


def run_test():
    R, C, H, V = map(int, raw_input().split())
    waffle = map(lambda _: [int(c == '@') for c in raw_input()], range(R))
    total_chips = count_chips(waffle)
    if total_chips == 0:
        return "POSSIBLE"
    cells = R * C
    if total_chips == cells:
        return "POSSIBLE"

    return "POSSIBLE" if check_horizontal(waffle, H, V) else "IMPOSSIBLE"


T = int(raw_input())
for i in range(1, T + 1):
    print("Case #{}: {}".format(i, run_test()))
