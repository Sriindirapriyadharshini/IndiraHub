package IndiraHub.config;

import IndiraHub.model.Product;
import IndiraHub.model.User;
import IndiraHub.repository.ProductRepository;
import IndiraHub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(ProductRepository productRepository, UserRepository userRepository) {
        return args -> {
            // Seed Demo Users if none exist
            if (userRepository.count() == 0) {
                userRepository.save(new User(
                        "demo",
                        "demo@indirahub.com",
                        "demo123",
                        "Demo User",
                        "CUSTOMER"
                ));

                userRepository.save(new User(
                        "admin",
                        "admin@indirahub.com",
                        "admin123",
                        "Indira Admin",
                        "ADMIN"
                ));
            }

            // Always repopulate full collection if count is less than 20
            if (productRepository.count() < 20) {
                productRepository.deleteAll();

                List<Product> fullCatalog = List.of(
                        // 1. ELECTRONICS
                        new Product(
                                "ProBook Ultra 16\" Laptop",
                                "Intel Core i7 13th Gen, 16GB DDR5 RAM, 1TB NVMe SSD, 16-inch 2.5K 120Hz IPS Display with backlit keyboard.",
                                45999.00,
                                "Electronics",
                                "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&w=700&q=80",
                                25,
                                4.8,
                                142,
                                "Bestseller"
                        ),
                        new Product(
                                "Nexus Neo 5G Smartphone",
                                "6.7-inch 120Hz AMOLED, 50MP Sony OIS Camera, Snapdragon 7+ Gen 3, 5000mAh Battery with 67W Turbo Charging.",
                                19999.00,
                                "Electronics",
                                "https://images.unsplash.com/photo-1598327105666-5b89351aff97?auto=format&fit=crop&w=700&q=80",
                                40,
                                4.6,
                                98,
                                "Hot"
                        ),
                        new Product(
                                "AcousticPro ANC Wireless Headphones",
                                "Active Noise Cancelling, Hi-Res Audio certification, 40-hour playback, ultra-soft memory foam earcups.",
                                2499.00,
                                "Electronics",
                                "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=700&q=80",
                                60,
                                4.7,
                                215,
                                "Top Rated"
                        ),
                        new Product(
                                "Apex Pulse Smartwatch",
                                "1.96-inch AMOLED Always-On Display, Bluetooth calling, 100+ Sports modes, SpO2 & 24/7 Heart Rate monitoring.",
                                1999.00,
                                "Electronics",
                                "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=700&q=80",
                                85,
                                4.5,
                                180,
                                "Popular"
                        ),
                        new Product(
                                "Vortex RGB Mechanical Keyboard",
                                "Hot-swappable Custom Linear Red switches, per-key RGB backlighting, aircraft-grade aluminum frame.",
                                899.00,
                                "Electronics",
                                "https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=700&q=80",
                                55,
                                4.6,
                                88,
                                "Trending"
                        ),
                        new Product(
                                "VisionView 27\" QHD Gaming Monitor",
                                "2560x1440 IPS Panel, 165Hz Refresh Rate, 1ms Response Time, HDR400, AMD FreeSync Premium support.",
                                16499.00,
                                "Electronics",
                                "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?auto=format&fit=crop&w=700&q=80",
                                18,
                                4.9,
                                64,
                                "New"
                        ),

                        // 2. HOME DECOR
                        new Product(
                                "Levitating Magnetic Moon Ambient Lamp",
                                "3D-printed realistic lunar surface floating mid-air via magnetic levitation, touch sensor with warm and cool glow modes.",
                                2999.00,
                                "Home Decor",
                                "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=700&q=80",
                                35,
                                4.9,
                                175,
                                "Trending"
                        ),
                        new Product(
                                "Orbital Zero-Gravity Ergonomic Lounge Chair",
                                "Weightless spine decompression posture design with breathable mesh upholstery, memory foam neck support pillow.",
                                18499.00,
                                "Home Decor",
                                "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?auto=format&fit=crop&w=700&q=80",
                                12,
                                4.8,
                                52,
                                "Luxury"
                        ),
                        new Product(
                                "Nordic Minimalist Ceramic Vase Trio",
                                "Set of 3 matte-finish handcrafted ceramic vases in neutral sandstone tones, geometric sculptural aesthetic.",
                                1299.00,
                                "Home Decor",
                                "https://images.unsplash.com/photo-1612196808214-b8e1d6145a8c?auto=format&fit=crop&w=700&q=80",
                                50,
                                4.7,
                                89,
                                "Editor's Pick"
                        ),
                        new Product(
                                "Sunset Atmosphere RGB Smart Projection Lamp",
                                "16-million color gradient sunset and golden hour projection with smartphone Bluetooth app controls.",
                                899.00,
                                "Home Decor",
                                "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?auto=format&fit=crop&w=700&q=80",
                                90,
                                4.6,
                                230,
                                "Bestseller"
                        ),

                        // 3. FITNESS
                        new Product(
                                "Pro-Series Gravity Inversion Boots",
                                "Heavy-duty aerospace-grade dual-lock alloy clamps with thick contoured ankle padding for spinal decompression and core workout.",
                                3799.00,
                                "Fitness",
                                "https://images.unsplash.com/photo-1517838277536-f5f99be501cd?auto=format&fit=crop&w=700&q=80",
                                30,
                                4.8,
                                78,
                                "Pro Fitness"
                        ),
                        new Product(
                                "Quick-Dial Adjustable Dumbbell Pair (24kg)",
                                "Rapid 15-in-1 weight dial adjustment system from 2.5kg to 24kg per dumbbell with non-slip knurled grip handle.",
                                7999.00,
                                "Fitness",
                                "https://images.unsplash.com/photo-1583454110551-21f2fa2afe61?auto=format&fit=crop&w=700&q=80",
                                20,
                                4.9,
                                110,
                                "Top Rated"
                        ),
                        new Product(
                                "Non-Slip High-Density TPE Yoga Mat 8mm",
                                "Laser-etched body alignment lines, dual-sided textured grip, eco-friendly tear-resistant cushioned joint protection.",
                                1199.00,
                                "Fitness",
                                "https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f?auto=format&fit=crop&w=700&q=80",
                                65,
                                4.7,
                                195,
                                "Popular"
                        ),
                        new Product(
                                "Smart Digital Jump Rope with Calorie Tracker",
                                "High-speed dual ball bearings, cordless and roped dual modes, backlit LCD screen displaying jump rotations and burn.",
                                749.00,
                                "Fitness",
                                "https://images.unsplash.com/photo-1518611012118-696072aa579a?auto=format&fit=crop&w=700&q=80",
                                80,
                                4.5,
                                142,
                                "Must Have"
                        ),

                        // 4. KITCHEN & BAKING
                        new Product(
                                "MasterBake Convection Air Fryer & Oven 12L",
                                "360° high-speed heat vortex convection, 16 smart presets for sourdough, rotisserie, pastries, and oil-free crisping.",
                                6499.00,
                                "Kitchen & Baking",
                                "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?auto=format&fit=crop&w=700&q=80",
                                22,
                                4.8,
                                95,
                                "Top Chef"
                        ),
                        new Product(
                                "Gravity-Defying Illusion Cake Craft Kit",
                                "Complete food-grade internal support structure armature, pouring candy spout mold, and premium silicone decorating tools.",
                                1499.00,
                                "Kitchen & Baking",
                                "https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=700&q=80",
                                45,
                                4.7,
                                64,
                                "Viral Hit"
                        ),
                        new Product(
                                "Japanese VG-10 Damascus Chef Knife 8\"",
                                "67 layers of forged Damascus steel, razor-sharp 15° hand-honed blade angle, balanced ergonomic pakkawood handle.",
                                3899.00,
                                "Kitchen & Baking",
                                "https://images.unsplash.com/photo-1593618998160-e34014e67546?auto=format&fit=crop&w=700&q=80",
                                35,
                                4.9,
                                160,
                                "Chef Choice"
                        ),
                        new Product(
                                "Pre-Seasoned Cast Iron Dutch Oven (5.5L)",
                                "Heavy-gauge enameled cast iron delivering superior heat distribution and retention for artisan bread and stews.",
                                2799.00,
                                "Kitchen & Baking",
                                "https://images.unsplash.com/photo-1584990347449-39958bc30953?auto=format&fit=crop&w=700&q=80",
                                28,
                                4.8,
                                115,
                                "Classic"
                        ),

                        // 5. BEAUTY & MAKEUP
                        new Product(
                                "Zero-G Weightless Velvet Matte Primer (30ml)",
                                "Ultra-lightweight pore-blurring formula enriched with niacinamide, creates a velvety smooth canvas that locks makeup 16 hours.",
                                1299.00,
                                "Beauty & Makeup",
                                "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?auto=format&fit=crop&w=700&q=80",
                                70,
                                4.8,
                                210,
                                "Bestseller"
                        ),
                        new Product(
                                "Celestial Chromatic 18-Color Eyeshadow Palette",
                                "Richly pigmented buttery mattes, molten multi-chromes, and high-shine foils for effortless day-to-night eye artistry.",
                                1699.00,
                                "Beauty & Makeup",
                                "https://images.unsplash.com/photo-1512496015851-a90fb38ba796?auto=format&fit=crop&w=700&q=80",
                                50,
                                4.7,
                                184,
                                "Trending"
                        ),
                        new Product(
                                "Botanical Glow Vitamin C Radiance Serum",
                                "15% Pure L-Ascorbic Acid infused with Hyaluronic Acid and Ferulic Acid to brighten skin tone and smooth fine lines.",
                                1499.00,
                                "Beauty & Makeup",
                                "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?auto=format&fit=crop&w=700&q=80",
                                60,
                                4.9,
                                320,
                                "Top Rated"
                        ),
                        new Product(
                                "Velvet Matte Moisture Lip Trio Set",
                                "Three universally flattering shades in a featherlight, non-drying transfer-resistant formula infused with jojoba oil.",
                                999.00,
                                "Beauty & Makeup",
                                "https://images.unsplash.com/photo-1586495777744-4413f21062fa?auto=format&fit=crop&w=700&q=80",
                                85,
                                4.6,
                                150,
                                "Hot Deal"
                        ),

                        // 6. APPAREL & FASHION
                        new Product(
                                "Sculpting Compression Seamless Midi Dress",
                                "Body-contouring 4-way micro-compression ribbed fabric, weightless breathability, and flattering silhouette for all occasions.",
                                2899.00,
                                "Apparel & Fashion",
                                "https://images.unsplash.com/photo-1539109136881-3be0616acf4b?auto=format&fit=crop&w=700&q=80",
                                40,
                                4.8,
                                130,
                                "Iconic"
                        ),
                        new Product(
                                "Minimalist 450 GSM French Terry Hoodie",
                                "Heavyweight combed organic cotton hoodie with dropped shoulders, double-layered hood, and clean tailored seams.",
                                1899.00,
                                "Apparel & Fashion",
                                "https://images.unsplash.com/photo-1556905055-8f358a7a47b2?auto=format&fit=crop&w=700&q=80",
                                55,
                                4.7,
                                175,
                                "Trending"
                        ),
                        new Product(
                                "AeroFlow Weatherproof Tech Performance Jacket",
                                "Ultralight windproof and water-resistant shell with magnetic storm flap and concealed zip utility pockets.",
                                2299.00,
                                "Apparel & Fashion",
                                "https://images.unsplash.com/photo-1544441893-675973e31985?auto=format&fit=crop&w=700&q=80",
                                35,
                                4.6,
                                92,
                                "New Season"
                        ),
                        new Product(
                                "Urban Edge Modular Crossbody Sling Bag",
                                "Water-repellent structured Cordura canvas with German Fidlock magnetic buckle, waterproof zippers, and tablet sleeve.",
                                1999.00,
                                "Apparel & Fashion",
                                "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?auto=format&fit=crop&w=700&q=80",
                                45,
                                4.8,
                                115,
                                "Bestseller"
                        ),

                        // 7. GROCERIES & SNACKS
                        new Product(
                                "Astro Orbit Freeze-Dried Real Fruit Medley (3pk)",
                                "100% whole strawberries, mango chunks, and bananas freeze-dried to crunchy perfection. Zero added sugar or preservatives.",
                                499.00,
                                "Groceries & Snacks",
                                "https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?auto=format&fit=crop&w=700&q=80",
                                120,
                                4.9,
                                280,
                                "Staff Pick"
                        ),
                        new Product(
                                "Roasted Himalayan Foxnut Makhana Trio (300g)",
                                "Slow-roasted lotus seeds in extra virgin olive oil: Peri-Peri Crunch, Himalayan Pink Salt, and Herbed Truffle Cheese.",
                                399.00,
                                "Groceries & Snacks",
                                "https://images.unsplash.com/photo-1599599810769-bcde5a160d32?auto=format&fit=crop&w=700&q=80",
                                150,
                                4.7,
                                340,
                                "Healthy Choice"
                        ),
                        new Product(
                                "Ceremonial Grade First-Harvest Matcha (50g)",
                                "Authentic Japanese single-origin shade-grown green tea ground to micro-fine powder with rich umami and vibrant emerald hue.",
                                899.00,
                                "Groceries & Snacks",
                                "https://images.unsplash.com/photo-1576092768241-dec231879fc3?auto=format&fit=crop&w=700&q=80",
                                75,
                                4.9,
                                190,
                                "Superfood"
                        ),
                        new Product(
                                "Single-Origin 72% Dark Chocolate Almond Bark",
                                "Stone-ground artisan Ecuadorian dark chocolate studded with slow-roasted California almonds and flaky sea salt crystals.",
                                449.00,
                                "Groceries & Snacks",
                                "https://images.unsplash.com/photo-1549007994-cb92caebd54b?auto=format&fit=crop&w=700&q=80",
                                100,
                                4.8,
                                215,
                                "Artisan"
                        ),

                        // 8. BOOKS
                        new Product(
                                "Mastering Java & Spring Boot",
                                "Comprehensive masterclass from core modern Java 21 concepts through robust Spring Boot microservices.",
                                599.00,
                                "Books",
                                "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?auto=format&fit=crop&w=700&q=80",
                                70,
                                4.9,
                                310,
                                "Bestseller"
                        ),
                        new Product(
                                "Python For Data Science & AI",
                                "Hands-on guide covering NumPy, Pandas, Scikit-Learn, neural networks, and modern LLM application workflows.",
                                699.00,
                                "Books",
                                "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&w=700&q=80",
                                95,
                                4.8,
                                260,
                                "Top Rated"
                        ),
                        new Product(
                                "Data Structures & Algorithms Made Easy",
                                "In-depth problem-solving patterns, tree traversals, dynamic programming, and FAANG interview readiness.",
                                749.00,
                                "Books",
                                "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=700&q=80",
                                65,
                                4.9,
                                412,
                                "Must Read"
                        ),
                        new Product(
                                "The Midnight Chronicle: Best-Selling Novel",
                                "An enchanting, award-winning international fiction bestseller about second chances, parallel lives, and hope.",
                                399.00,
                                "Books",
                                "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?auto=format&fit=crop&w=700&q=80",
                                80,
                                4.7,
                                340,
                                "Staff Pick"
                        )
                );

                productRepository.saveAll(fullCatalog);
            }
        };
    }
}
