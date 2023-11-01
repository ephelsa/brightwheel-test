package com.github.ephelsa.brightwheelexercise.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ephelsa.brightwheelexercise.R
import com.github.ephelsa.brightwheelexercise.ui.theme.AppColor
import com.github.ephelsa.brightwheelexercise.ui.theme.Space

@Composable
fun RepositoryCard(
    modifier: Modifier = Modifier,
    fullName: String,
    starCount: Long,
    topContributor: String?,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Space.Large, vertical = Space.Medium)

        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.github_logo),
                contentDescription = stringResource(id = R.string.contentDescription_github),
                modifier = Modifier
                    .padding(end = Space.XSmall)
                    .size(20.dp)
            )
            Text(text = fullName, style = MaterialTheme.typography.labelLarge)
        }

        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(id = R.string.contentDescription_stars),
                modifier = Modifier
                    .padding(end = Space.XSmall)
                    .size(20.dp),
                tint = AppColor.HunyadiYellow
            )
            Text(text = starCount.toString(), style = MaterialTheme.typography.labelLarge)
        }

        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (topContributor == null) {
                val loadingColor = Color.LightGray

                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = Space.Small)
                        .size(20.dp),
                    color = loadingColor
                )
                Text(
                    text = stringResource(id = R.string.loading_contributor),
                    style = MaterialTheme.typography.labelLarge.copy(color = loadingColor)
                )
            } else if (topContributor.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Face,
                    contentDescription = stringResource(id = R.string.contentDescription_topContributor),
                    modifier = Modifier
                        .padding(end = Space.XSmall)
                        .size(20.dp),
                    tint = AppColor.Pink40
                )
                Text(text = topContributor, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Preview()
@Composable
private fun RepositoryCardPreview() {
    RepositoryCard(fullName = "johndoe/loremipsum", starCount = 120, topContributor = "ephelsa")
}