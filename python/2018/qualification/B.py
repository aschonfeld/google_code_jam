def run_test():
    N = int(raw_input())
    V = [int(v) for v in raw_input().split()]
    V = trouble_sort(V)
    return next((i for i in range(len(V)-1) if V[i] > V[i+1]), "OK")


def trouble_sort(V):
    done = False
    while not done:
        done = True
        for i in range(len(V) - 2):
            if V[i] > V[i+2]:
                done = False
                V = V[:i] + V[i:i+3][::-1] + V[i+3:]
    return V

T = int(raw_input())
for t in range(1, T+1):
    print("Case #{}: {}".format(t, run_test()))

# with open('A.out', "w+") as f:
#     for i in range(1, int(raw_input()) + 1):
#         f.write("Case #{}: {}\n".format(i, run_test()))
