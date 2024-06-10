# River Crossing Optimization

## Project Overview

This program provides a solution to the classic river crossing problem for a family of N members. The objective is to determine the optimal way for all family members to cross a river using a single torch, in the shortest possible time.

## Features

- Input:
  - Number of family members (N)
  - Name of each member
  - Speed at which each member crosses the river

- Algorithm:
  - Uses the A* algorithm to calculate the optimal solution.
  - The heuristic function considers the cost of each transfer.
  - When two members cross together, the program takes the speed of the slower member as the cost.

## How It Works

The program calculates the optimal crossing strategy by:
1. Accepting the number of family members and their respective crossing speeds.
2. Utilizing the A* algorithm to evaluate the best possible crossing sequence.
3. Ensuring that the heuristic is admissible by considering the maximum time required for each step.

## Example

Given a family with 3 members having speeds 1, 2, and 5, the program will output the optimal sequence of crossings to minimize the total time required.
