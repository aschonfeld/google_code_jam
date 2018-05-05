def build_stack(stack, ants, N):
    if not len(ants):
        return len(stack)
    selected_ant = None
    for i, ant in enumerate(ants):
        l, wt = ant
        if l < stack[-1][1][0] and all(curr_wt + wt <= (6 * curr_ant[1]) for curr_wt, curr_ant in stack):
            selected_ant = i
            break
    if selected_ant is not None:
        return build_stack(stack + [(stack[-1][0] + ants[i][1], ants[i])], [a for i, a in enumerate(ants) if i != selected_ant], N)
    else:
        return len(stack)

def run_test():
    N = int(raw_input())
    ants = sorted(((length+1, wt) for length, wt in enumerate(map(int, raw_input().split()))), key=lambda (l, _): l, reverse=True)
    best_ant = ants[0]
    return build_stack([(best_ant[1], best_ant)], ants[1:], N)


T = int(raw_input())
for i in range(1, T + 1):
    print("Case #{}: {}".format(i, run_test()))