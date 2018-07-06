# author - Michele Piccolini (mujina93)

# given a string S and a list of word D, find the
# word (from D) which is a substring (not a consecutive
# subsequence) of S and
# which is the longest among the ones that you can
# find in S

# Solution 4

import time # for execution time
from math import floor

# data
def init():
    S = 'abppplee'
    D = ['able','ale','apple','bale','kangaroo','hallelujahuh']
    return S, D

class count():
    def __init__(self,word,count):
        self.w = word
        self.i = count
    def __repr__(self):
        return "("+self.w+","+str(self.i)+")"

def add_counts(D):
    # O(len(D))
    tuples = []
    for word in D:
        tuples.append(count(word,0))
    return tuples

def efficient_find(S,D):
    # O(len(S)*len(D)/A)
    
    def insert(key, val, group):
        if key not in group:
            group[key] = [val]
        else:
            group[key].append(val)

    tuples = add_counts(D)
    group_by_index = {}
    for t in tuples: # O(len(D))
        letter_at_i = t.w[t.i]
        insert(letter_at_i, t, group_by_index)
    print("Initial map with counts and frontier letters:")
    for key, val in group_by_index.items():
        print(key," ",val)
    
    longest_word = ''
    print("Start scanning S and advancing words' frontiers")
    for i, letter in enumerate(S): # O(len(S)*...)
        marked_for_removal = []
        print("letter ",letter,end=' ')
        # look for words that were looking for this letter to advance
        if letter in group_by_index:
            print(group_by_index[letter])
            for t in group_by_index[letter]: # O(len(D)/A) with A alphabet's size
                #print("Considering",t.w,end=' ')
                # if this was the last letter, you completed this word!
                t.i += 1 # increase count of tuples that were looking for this letter    
                if t.i == len(t.w):
                    #print("Word completed!")
                    if len(t.w) > len(longest_word):
                        #print("It's the new longest!")
                        longest_word = t.w
                    marked_for_removal.append(t)
                else:
                    next_letter = t.w[t.i]
                    if next_letter != letter:
                        # move t in the new bin (whose key is the letter t is looking for)
                        insert(next_letter, t, group_by_index)
                        # remove it from last bin
                        marked_for_removal.append(t)
                    #print("Moving to bin: ",next_letter)
            for t in marked_for_removal:
                group_by_index[letter].remove(t)
            #print(group_by_index[letter])
    return longest_word if longest_word != '' else None               

def run():
    S, D = init()             
    res = efficient_find(S,D)
    print("Longest substring is: "+res) if res is not None else print("No word found in the string")

# execution
start = time.time()
run()
end = time.time()
print("Execution time: {:f}".format(end-start))