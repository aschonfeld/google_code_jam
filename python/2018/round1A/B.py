def load_cashier():
    M, S, P = map(int, raw_input().split())
    return dict(bits=M, time=S+P)


def run_test():
    R, B, C = map(int, raw_input().split())
    cashiers = {i: load_cashier() for i in range(R)}
    return 0

# unfinished solution
T = int(raw_input())
for i in range(1, T + 1):
    print("Case #{}: {}".format(i, run_test()))
