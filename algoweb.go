package algoweb

import (
	"crypto/rand"
	"crypto/rsa"
	"log"

	"github.com/gofiber/fiber/v2"
	jwtware "github.com/gofiber/jwt/v3"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type AlgoWeb struct {
	app        *fiber.App
	db         *gorm.DB
	privateKey *rsa.PrivateKey
}

func New() *AlgoWeb {
	app := fiber.New()
	app.Static("/", "./resources/build")

	dsn := "root:1024@tcp(127.0.0.1:3306)/algo?charset=utf8&parseTime=True&loc=Local"
	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatalln(err)
	}
	db.AutoMigrate(&User{})

	rng := rand.Reader
	privateKey, err := rsa.GenerateKey(rng, 2048)
	if err != nil {
		log.Fatalf("rsa.GenerateKey: %v", err)
	}

	return &AlgoWeb{app, db, privateKey}
}

func (w *AlgoWeb) Run() {
	userController := UserController{w.db, w.privateKey}

	api := w.app.Group("/api")
	v1 := api.Group("/v1")

	v1.Post("/auth", userController.auth)
	v1.Use(jwtware.New(jwtware.Config{
		SigningMethod: "RS256",
		SigningKey:    w.privateKey.Public(),
	}))
	v1.Get("/me", userController.me)

	w.app.Get("/test", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{
			"result":  true,
			"message": "테스트 중입니다.",
		})
	})

	w.app.Get("/*", func(c *fiber.Ctx) error {
		return c.SendFile("./resources/build/index.html")
	})
	w.app.Listen(":80")
}
