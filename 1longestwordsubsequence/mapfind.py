# author - Michele Piccolini (mujina93)

# given a string S and a list of word D, find the
# word (from D) which is a substring (not a consecutive
# subsequence) of S and
# which is the longest among the ones that you can
# find in S

# Solution 2
# mapping S to a map<letter,ordered_occurrency_list> for fast lookup
# and employing a binary search on ordered_occurency_list to find indexes faster

import time # for execution time
from math import floor

# data
def init():
    S = 'abppplee'
    D = ['able','ale','apple','bale','kangaroo','hallelujahuh']
    return S, D

def clean(S,D):
    # O(len(D)*log(len(D))) = O(L*log(L))

    # filter out words that are too long
    N = len(S)
    fD = list(filter(lambda str: len(str)<=N, D)) # O(len(D))
    print("Filtered: ", fD)

    # sort the words by lenght, so that you look for the longest before looking for
    # the shortest. If you find one, you know that that is immediately the longest
    # subsequence in S.
    sD = sorted(fD, key=len, reverse=True) # O(len(D)*log(len(D)))
    print("Sorted: ", sD)

    return sD

# build a map of S, a dictionary representing the information
# in the most efficient way possible for our problem
def decompose(S):
    # O(len(S))
    print("Creating a positional map out of "+S+" (alternative representation for the string):")
    map = {}
    for i, letter in enumerate(S):
        if letter in map:
            map[letter].append(i)
        else:
            map[letter] = [i]
    for letter, position_list in map.items():
        print(letter, position_list)
    return map

def smallest_bigger_binsearch(olist, key, smallest_bigger=None):
    # O(log(len(olist)))
    L = 0
    R = len(olist)
    C = floor((L+R)/2) # middle point
    #print("key: ",key)
    #print("bin: ",C," ",olist[C])
    if olist[C] > key:
        smallest_bigger = olist[C]
        #print("new smallest bigger: ",smallest_bigger)
        if R - L > 1: # if there is room for searching
            smallest_bigger = smallest_bigger_binsearch(olist[L:C], key, smallest_bigger) # look left to find smaller bigger values
    elif olist[C] <= key: 
        if R - L > 1: # if there is room for searching
            smallest_bigger = smallest_bigger_binsearch(olist[C:R], key, smallest_bigger) # look right to start finding bigger values
    return smallest_bigger

def mapfind(S,D):
    # O(len(D)*log(len(D)+len(S)+len(D)*log(len(S))) = O(len(S) + len(D)*log(len(S)*len(D)))
    # O(N + L*log(NL))

    # clean and sort words (longer sooner)
    D = clean(S,D)  # O(L*log(L))

    # map S into a better representation (for fast lookup)
    map = decompose(S) # O(len(S))

    # look if the word is a substring of S by queriend the map
    for word in D:  # O(len(D)*...)
        print("CHECK: "+word)
        found_valid = False
        index_of_last_valid_letter = -1
        for j,letter in enumerate(word):
            print(letter)
            if letter in map:
                where_in_S = map[letter] # positions of 'letter' in S. One or more may be 'valid'.
                print("is present at ",where_in_S)
                # look for the first valid letter (the letter must be in S, and it must be
                # it must come after the last valid letter that was found in S)
                index_of_next_valid_letter = smallest_bigger_binsearch(where_in_S, index_of_last_valid_letter) # O(log(len(where_in_S))) ~ O(log(len(S)))
                if index_of_next_valid_letter is None: # you did not find a next valid letter
                    print("no valid letter was found. Stop searching for this word.")
                    break # exit the for letter in word loop, i.e. stop considering this word, it's not there
                else: # you found a next valid letter
                    index_of_last_valid_letter = index_of_next_valid_letter # remember this index
                    found_valid = True # you found a valid one
                    print("valid position found: ",index_of_last_valid_letter)
            else:
                print("no such letter in the string")
                break
        if found_valid: # if the last letter of the word was found in S (and it was valid, i.e. it came after the other letters)
            return word # immediately return (since D was ordered, this is the longest one)
    # if nothing was found
    return None

def run():
    S, D = init()             
    res = mapfind(S,D)
    print("Longest substring is: "+res) if res is not None else print("No word found in the string")

# execution
start = time.time()
run()
end = time.time()
print("Execution time: {:f}".format(end-start))