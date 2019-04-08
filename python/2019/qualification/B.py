def run_test():
    N = int(raw_input())
    P = raw_input()
    lydia_points = list(make_lydia_points(P, N))
    lydia = zip(lydia_points[:-1], lydia_points[1:])
    moves = set()
    if N <= 2:
        return find_path(N, lydia, (0, 0), '', moves, True)

    path = ''
    x, y = 0, 0
    for i, lp in enumerate(lydia_points[1:], 1):
        try:
            test_east = go_east((x, y), N)
            if test_east != lp:
                path += 'E'
                x, y = test_east
                continue
        except:
            pass
        try:
            test_south = go_south((x, y), N)
            if test_south != lp:
                path += 'S'
                x, y = test_south
                continue
        except:
            raise Exception
    return path


def go_south((x, y), N):
    if y + 1 == N:
        raise Exception('bad move {}, {}'.format(y + 1, x))
    return x, y + 1


def go_east((x, y), N):
    if x + 1 == N:
        raise Exception('bad move {}, {}'.format(x + 1, y))
    return x + 1, y


def is_done((x, y), N):
    return x == N - 1 and y == N - 1


def make_lydia_points(P, N):
    x, y = 0, 0
    yield x, y
    for m in P:
        if m == 'S':
            x, y = go_south((x, y), N)
        if m == 'E':
            x, y = go_east((x, y), N)
        yield x, y


def find_path(N, lydia, curr_pt, path, moves, toggle):
    if is_done(curr_pt, N):
        return path

    def tester1(): # SOUTH/EAST
        try:
            test_south = go_south(curr_pt, N)
            if (curr_pt, test_south) not in lydia and (curr_pt, test_south) not in moves:
                moves.add((curr_pt, test_south))
                return find_path(N, lydia, test_south, path + 'S', moves, not toggle)
        except:
            pass
        try:
            test_east = go_east(curr_pt, N)
            if (curr_pt, test_east) not in lydia and (curr_pt, test_east) not in moves:
                moves.add((curr_pt, test_east))
                return find_path(N, lydia, test_east, path + 'E', moves, not toggle)
        except:
            pass
        raise Exception

    def tester2(): # EAST/SOUTH
        try:
            test_east = go_east(curr_pt, N)
            if (curr_pt, test_east) not in lydia and (curr_pt, test_east) not in moves:
                moves.add((curr_pt, test_east))
                return find_path(N, lydia, test_east, path + 'E', moves, not toggle)
        except:
            pass
        try:
            test_south = go_south(curr_pt, N)
            if (curr_pt, test_south) not in lydia and (curr_pt, test_south) not in moves:
                moves.add((curr_pt, test_south))
                return find_path(N, lydia, test_south, path + 'S', moves, not toggle)
        except:
            pass
        raise Exception

    if toggle:
        return tester1()
    return tester2()


T = int(raw_input())
for t in range(1, T+1):
    print("Case #{}: {}".format(t, run_test()))

# with open('A.out', "w+") as f:
#     for i in range(1, int(raw_input()) + 1):
#         f.write("Case #{}: {}\n".format(i, run_test()))
