package com.example.resepku_jetpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen(viewModel: MealViewModel = viewModel()) {
    val mealList by viewModel.randomMeal.observeAsState(initial = null)
    val popularMeals by viewModel.popularMeals.observeAsState(initial = emptyList())
    val categories by viewModel._categories.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchRandomMeal()
        viewModel.fetchPopularItems(category = "Seafood")
        viewModel.fetchCategories()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Header section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ResepKu",
                fontSize = 21.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(3f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                modifier = Modifier.size(30.dp)
            )
        }

        Text(
            text = "Choose Today's Recipe",
            fontSize = 13.sp,
            fontFamily = FontFamily.Default,
            modifier = Modifier.padding(vertical = 5.dp)
        )

        // Random Recipe section
        if (mealList != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 10.dp),
                elevation = 20.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    model = mealList!!.meals[0].strMealThumb,
                    contentDescription = "Random Food Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Food categories section
        Text(
            text = "Food Categories",
            fontSize = 13.sp,
            fontFamily = FontFamily.Default,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories?.let {
                items(it.size) { index ->
                    val category = categories?.get(index)
                    if (category != null) {
                        CategoryItem(category)
                    }
                }
            }
        }

        // Popular recipes section
        Text(
            text = "Popular Recipes",
            fontSize = 13.sp,
            fontFamily = FontFamily.Default,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        // Display the grid of popular recipes
        popularMeals?.let { PopularRecipesGrid(it) }
    }
}

@Composable
fun PopularRecipesGrid(popularMeals: List<CategoryMeal>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(popularMeals.size) { index ->
            val meal = popularMeals[index]
            RecipeItem(meal = meal)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(4.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = category.strCategoryThumb,
                contentDescription = "Category Image",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.strCategory,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun RecipeItem(meal: CategoryMeal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(215.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = "Meal Image",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = meal.strMeal,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
