<?php

namespace App\Entity;

use App\Repository\TicketRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: TicketRepository::class)]
class Ticket
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $dispoTicket = null;

    #[ORM\Column]
    private ?int $nbMaxTicket = null;

    #[ORM\ManyToOne(inversedBy: 'ticket')]
    private ?Evenement $evenement = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDispoTicket(): ?string
    {
        return $this->dispoTicket;
    }

    public function setDispoTicket(string $dispoTicket): self
    {
        $this->dispoTicket = $dispoTicket;

        return $this;
    }

    public function getNbMaxTicket(): ?int
    {
        return $this->nbMaxTicket;
    }

    public function setNbMaxTicket(int $nbMaxTicket): self
    {
        $this->nbMaxTicket = $nbMaxTicket;

        return $this;
    }

    public function getEvenement(): ?Evenement
    {
        return $this->evenement;
    }

    public function setEvenement(?Evenement $evenement): self
    {
        $this->evenement = $evenement;

        return $this;
    }
}
