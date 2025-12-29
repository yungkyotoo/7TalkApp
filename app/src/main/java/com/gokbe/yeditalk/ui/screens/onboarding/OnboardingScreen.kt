package com.gokbe.yeditalk.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gokbe.yeditalk.ui.theme.BlueYeditepe
import com.gokbe.yeditalk.ui.theme.GreenYeditepe

enum class OnboardingStep {
    INFO,
    TITLE_SELECTION,
    COMMUNITY_SELECTION,
    COMPLETED
}

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit
) {
    var currentStep by remember { mutableStateOf(OnboardingStep.INFO) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // Light blue background similar to designs
    ) {
        // Background content (blurred or static application background) can go here
        
        when (currentStep) {
            OnboardingStep.INFO -> {
                InfoPopup(
                    onDismiss = { /* Cannot dismiss without action? */ },
                    onNext = { currentStep = OnboardingStep.TITLE_SELECTION }
                )
            }
            OnboardingStep.TITLE_SELECTION -> {
                TitleSelectionPopup(
                    onDismiss = { /* Optional: Go back? */ },
                    onConfirm = { currentStep = OnboardingStep.COMMUNITY_SELECTION }
                )
            }
            OnboardingStep.COMMUNITY_SELECTION -> {
                CommunitySelectionPopup(
                    onDismiss = { /* Optional: Go back? */ },
                    onConfirm = {
                        currentStep = OnboardingStep.COMPLETED
                        onOnboardingComplete()
                    }
                )
            }
            OnboardingStep.COMPLETED -> {
                // Should navigate away
            }
        }
    }
}

@Composable
fun InfoPopup(
    onDismiss: () -> Unit,
    onNext: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon or Image placeholder
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.LightGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = BlueYeditepe,
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "YediTalk'a Hoşgeldiniz!",
                    style = MaterialTheme.typography.titleLarge,
                    color = BlueYeditepe,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Üniversite e-posta adresinizle kayıt olarak topluluğa katılabilirsiniz. Anonim olarak paylaşım yapabilir veya kimliğinizi açıklayabilirsiniz.",
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = onNext,
                    colors = ButtonDefaults.buttonColors(containerColor = GreenYeditepe),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Devam Et")
                }
            }
        }
    }
}

@Composable
fun TitleSelectionPopup(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val titles = listOf(
        "Öğrenci",
        "Akademisyen",
        "Mezun",
        "Personel"
    )
    var selectedTitle by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Unvanını Seç",
                    style = MaterialTheme.typography.titleLarge,
                    color = BlueYeditepe,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyColumn(
                    modifier = Modifier.height(200.dp) // Limit height
                ) {
                    items(titles) { title ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTitle = title }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedTitle == title,
                                onCheckedChange = { selectedTitle = title },
                                colors = CheckboxDefaults.colors(checkedColor = GreenYeditepe)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = title, fontSize = 16.sp)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onConfirm,
                    enabled = selectedTitle.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenYeditepe),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Seçimi Onayla")
                }
            }
        }
    }
}

@Composable
fun CommunitySelectionPopup(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val communities = listOf(
        "Yazılım Kulübü",
        "Dans Kulübü",
        "Müzik Kulübü",
        "Tiyatro Kulübü",
        "Sinema Kulübü"
    )
    // Multi-select possible or single? Assuming multiple for community
    val selectedCommunities = remember { mutableListOf<String>() }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Topluluklara Katıl",
                    style = MaterialTheme.typography.titleLarge,
                    color = BlueYeditepe,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyColumn(
                    modifier = Modifier.height(250.dp)
                ) {
                    items(communities) { community ->
                        var isSelected by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { 
                                    isSelected = !isSelected
                                    if (isSelected) selectedCommunities.add(community) else selectedCommunities.remove(community)
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { 
                                    isSelected = it
                                    if (it) selectedCommunities.add(community) else selectedCommunities.remove(community)
                                },
                                colors = CheckboxDefaults.colors(checkedColor = GreenYeditepe)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = community, fontSize = 16.sp)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = GreenYeditepe),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tamamla")
                }
            }
        }
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    OnboardingScreen(onOnboardingComplete = {})
}
