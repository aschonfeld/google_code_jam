import sys
import random


def run_test(customers):
    all_flavors = {f: 0 for f in range(1, customers+1)}
    remaining_flavors = all_flavors.keys()
    for customer in range(customers):
        flavors = map(int, raw_input().split())
        if flavors[0] == -1:
            return
        if flavors[0] == 0:
            print '-1'
            sys.stdout.flush()
            continue

        flavors = flavors[1:]
        for f in flavors:
            if f in all_flavors:
                all_flavors[f] += 1
        best_flavors = sorted([k for k in flavors if k in remaining_flavors], key=lambda k: all_flavors[k])
        selected_flavor = None
        if len(best_flavors):
            selected_flavor = best_flavors[0]
            remaining_flavors = [f for f in remaining_flavors if f != selected_flavor]
        print selected_flavor or '-1'
        sys.stdout.flush()

T = int(raw_input())
for t in range(1, T+1):
    run_test(int(raw_input()))