package daily.medium;

import java.util.*;

public class FindAllRecipes2115 {
    public static void main(String[] args) {
        List<List<String>> ingredients = new ArrayList<>();
        ingredients.add(List.of("yeast","flour"));
        String[] supplies = {"yeast","flour","corn"};
        String[] recipes = {"bread"};
        System.out.println(findAllRecipes(recipes, ingredients, supplies));
    }

//    topological sort; time: O(m+n+s), space: O(m+n+s)
    public static List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
//        store available supplies
        Set<String> availableSupplies = new HashSet<>(Arrays.asList(supplies));
//        map recipe to index
        Map<String, Integer> recipeToIdx = new HashMap<>();
//        map ingredient to recipes that need it
        Map<String, List<String>> dependencyGraph = new HashMap<>();
//        create recipe to index mapping
        for(int i = 0 ; i < recipes.length ; i++)
            recipeToIdx.put(recipes[i], i);
//        count of non supply ingredients needed for each recipe
        int[] inDegree = new int[recipes.length];
//        build dependencyGraph
        for(int recipeIdx = 0 ; recipeIdx < recipes.length ; recipeIdx++) {
            for(String ingredient : ingredients.get(recipeIdx)) {
                if(!availableSupplies.contains(ingredient)) {
                    dependencyGraph.computeIfAbsent(ingredient, k -> new ArrayList<>())
                            .add(recipes[recipeIdx]);
                    inDegree[recipeIdx]++;
                }
            }
        }
//        start with recipes that only need supplies
        Deque<Integer> queue = new ArrayDeque<>();
        for(int recipeIdx = 0 ; recipeIdx < recipes.length ; recipeIdx++) {
            if(inDegree[recipeIdx] == 0)
                queue.offer(recipeIdx);
        }
//        process recipes in topological order
        List<String> createdRecipes = new ArrayList<>();
        while(!queue.isEmpty()) {
            int recipeIdx = queue.poll();
            String recipe = recipes[recipeIdx];
            createdRecipes.add(recipe);
//            skip if no recipes depend on this one
            if(!dependencyGraph.containsKey(recipe)) continue;
//            update recipes that depend on the current recipe
            for(String dependentRecipe : dependencyGraph.get(recipe)) {
                if(--inDegree[recipeToIdx.get(dependentRecipe)] == 0)
                    queue.offer(recipeToIdx.get(dependentRecipe));
            }
        }
        return createdRecipes;
    }
}

/*
You have information about n different recipes. You are given a string array recipes and a 2D string array ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients from ingredients[i]. A recipe can also be an ingredient for other recipes, i.e., ingredients[i] may contain a string that is in recipes.
You are also given a string array supplies containing all the ingredients that you initially have, and you have an infinite supply of all of them.
Return a list of all the recipes that you can create. You may return the answer in any order.
Note that two recipes may contain each other in their ingredients.
Example 1:
Input: recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
Output: ["bread"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
Example 2:
Input: recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
Example 3:
Input: recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich","burger"]
Explanation:
We can create "bread" since we have the ingredients "yeast" and "flour".
We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".
We can create "burger" since we have the ingredient "meat" and can create the ingredients "bread" and "sandwich".

Constraints:
n == recipes.length == ingredients.length
1 <= n <= 100
1 <= ingredients[i].length, supplies.length <= 100
1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters.
All the values of recipes and supplies combined are unique.
Each ingredients[i] does not contain any duplicate values.
 */

/*
topological sort:
Let n be the number of recipes, m be the total number of ingredients across all recipes, and s be the number of supplies.
Time complexity: O(n+m+s)
Initially, we process all supplies to mark them as available, taking O(s) time. Then we create recipe mappings in O(n) time. Building the dependency graph requires examining each ingredient for each recipe once, taking O(m) time. When processing recipes in topological order, we visit each recipe once and process its dependencies. Since each ingredient-to-recipe edge in the dependency graph is processed exactly once, and the total number of such edges is bounded by m, the queue processing takes O(n+m) time. Therefore, the total time complexity is O(n+m+s).
 */