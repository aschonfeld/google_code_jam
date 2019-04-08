import sys


def run_test(t):
    N = int(raw_input())
    P = raw_input()
    sys.stdout.write("Case #{}: ".format(t))
    for m in P:
        sys.stdout.write('S' if m == 'E' else 'E')
    sys.stdout.write('\n')


T = int(raw_input())
for t in range(1, T+1):
    run_test(t)

# with open('A.out', "w+") as f:
#     for i in range(1, int(raw_input()) + 1):
#         f.write("Case #{}: {}\n".format(i, run_test()))
