import "./App.css"
import { useTypewriter } from "react-simple-typewriter"
import Profile from "./PortfolioContainer/Home/Profile"

function App() {
  const [typeEffect] = useTypewriter({
    words: [
      "FULL STACK DEVELOPER 👨‍💻",
      "ANDROID DEVELOPER 📱",
      "MERN STACK DEVELOPER 🌐",
    ],
    loop: {},
    typeSpeed: 100,
    deleteSpeed: 40,
  })
  return (
    <div className="App">
      <h1 style={{ color: "red" }}>
        <span>
          I'M A
          <span
            style={{ fontweight: "bold", color: "green", marginLeft: "5px" }}
          >
            {typeEffect}
          </span>
        </span>
      </h1>
      <Profile />
    </div>
  )
}

export default App
